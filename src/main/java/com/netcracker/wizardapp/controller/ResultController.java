package com.netcracker.wizardapp.controller;

import com.netcracker.wizardapp.domain.Result;
import com.netcracker.wizardapp.payload.request.ResultRequest;
import com.netcracker.wizardapp.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/result")
public class ResultController {
    @Autowired
    ResultService resultService;

    @PostMapping("/create")
    public ResponseEntity<?> createResult(@RequestBody ResultRequest resultView) {
        return resultService.createResult(resultView);
    }

    @GetMapping("findall")
    public Iterable<Result> findResultAll() {
        return resultService.findResultAll();
    }

    @GetMapping("findformoder/{id}")
    public Iterable<Result> findResultforModer(@PathVariable("id") Long id) {
        return resultService.findResultforModer(id);
    }

    @GetMapping("findforuser/{id}")
    public Iterable<Result> findResultForUser(@PathVariable("id") Long id) {
        return resultService.findResultForUser(id);
    }

}
