package com.netcracker.wizardapp.controller;

import com.netcracker.wizardapp.domain.*;
import com.netcracker.wizardapp.payload.request.ResultRequest;
import com.netcracker.wizardapp.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/result")
public class ResultController {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private ResultRepo resultRepo;
    @Autowired
    private PageRepo pageRepo;
    @Autowired
    private ButtonRepo buttonRepo;
    @Autowired
    private WizardRepo wizardRepo;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/create")
    public ResponseEntity<?> createResult(@RequestBody ResultRequest resultView) {
        Result result = new Result();
        User creator = userRepo.findById(resultView.getUser_id()).orElseThrow(() -> new RuntimeException("User Not Found with id: " + resultView.getUser_id()));
        Wizard wizard = wizardRepo.findById(resultView.getWizard_id()).orElseThrow(() -> new RuntimeException("Wizard Not Found with id: " + resultView.getWizard_id()));
        result.setUser(creator);
        result.setWizard(wizard);
        result.setDate(LocalDateTime.now());
        List<Note> notes = new ArrayList<>();
        for (Note note : resultView.getNotes()) {
            Page page = pageRepo.findById(note.getPage().getId()).orElseThrow(() -> new RuntimeException("Page Not Found with id: " + note.getPage().getId()));
            Button button = buttonRepo.findById(note.getButton().getId()).orElseThrow(() -> new RuntimeException("Button Not Found with id: " + note.getButton().getId()));
            notes.add(new Note(page,button));
            System.out.println(page.getId() + page.getName());
            System.out.println(button.getId() + button.getName());
        }
        result.setNote(notes);
        resultRepo.save(result);
        return ResponseEntity.ok().body(result);

    }

    @GetMapping("findall")
    public Iterable<Result> findResultAll() {
        return resultRepo.findAll();
    }

    @GetMapping("findformoder/{id}")
    public Iterable<Result> findResultforModer(@PathVariable("id") Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
        logger.info("Log4j2ExampleApp started.");
        logger.warn("Something to warn");
        logger.error("Something failed.");
        return resultRepo.findByCreator(user);
    }

    @GetMapping("findforuser/{id}")
    public Iterable<Result> findResultForUser(@PathVariable("id") Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
        return resultRepo.findAllByUser(user);
    }

}
