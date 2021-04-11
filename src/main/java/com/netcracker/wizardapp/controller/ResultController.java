package com.netcracker.wizardapp.controller;

import com.netcracker.wizardapp.domain.Result;
import com.netcracker.wizardapp.domain.User;
import com.netcracker.wizardapp.domain.Wizard;
import com.netcracker.wizardapp.payload.request.ResultRequest;
import com.netcracker.wizardapp.repository.ResultRepo;
import com.netcracker.wizardapp.repository.UserRepo;
import com.netcracker.wizardapp.repository.WizardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/result")
public class ResultController {
    @Autowired
    private ResultRepo resultRepo;
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
        result.setNote(resultView.getNotes());
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
        return resultRepo.findByCreator(user);
    }

    @GetMapping("findforUser/{id}")
    public Iterable<Result> findResultForUser(@PathVariable("id") Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
        return resultRepo.findAllByUser(user);
    }

}
