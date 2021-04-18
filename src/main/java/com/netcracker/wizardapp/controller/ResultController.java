package com.netcracker.wizardapp.controller;

import com.netcracker.wizardapp.domain.*;
import com.netcracker.wizardapp.exceptions.ButtonNotFoundException;
import com.netcracker.wizardapp.exceptions.PageNotFoundException;
import com.netcracker.wizardapp.exceptions.UserNotFoundException;
import com.netcracker.wizardapp.exceptions.WizardNotFoundException;
import com.netcracker.wizardapp.payload.request.ResultRequest;
import com.netcracker.wizardapp.payload.response.MessageResponse;
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
        User creator = userRepo.findById(resultView.getUser_id()).orElseThrow(() -> new UserNotFoundException("User Not Found with id: " + resultView.getUser_id()));
        Wizard wizard = wizardRepo.findById(resultView.getWizard_id()).orElseThrow(() -> new WizardNotFoundException("Wizard Not Found with id: " + resultView.getWizard_id()));//() -> new RuntimeException("Wizard Not Found with id: " + resultView.getWizard_id())
        result.setUser(creator);
        result.setWizard(wizard);
        result.setDate(LocalDateTime.now());
        List<Note> notes = new ArrayList<>();
        for (Note note : resultView.getNotes()) {
            Page page = pageRepo.findById(note.getPage().getId()).orElseThrow(() -> new PageNotFoundException("Page Not Found with id: " + note.getPage().getId()));
            Button button = buttonRepo.findById(note.getButton().getId()).orElseThrow(() -> new ButtonNotFoundException("Button Not Found with id: " + note.getButton().getId()));
            notes.add(new Note(page, button));
            logger.info("On the Page with id: " + page.getId() + ", name: " + page.getName() + " a button with id: " + button.getId() + ", name: " + button.getName() + " was clicked");
        }
        result.setNote(notes);
        resultRepo.save(result);
        return ResponseEntity.ok(new MessageResponse("Result created successfully!"));

    }

    @GetMapping("findall")
    public Iterable<Result> findResultAll() {
        return resultRepo.findAll();
    }

    @GetMapping("findformoder/{id}")
    public Iterable<Result> findResultforModer(@PathVariable("id") Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found with id: " + id));
        logger.info("User was found with name: " + user.getUsername());
        return resultRepo.findByCreator(user);
    }

    @GetMapping("findforuser/{id}")
    public Iterable<Result> findResultForUser(@PathVariable("id") Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found with id: " + id));
        logger.info("User was found with name: " + user.getUsername());
        return resultRepo.findAllByUser(user);
    }

}
