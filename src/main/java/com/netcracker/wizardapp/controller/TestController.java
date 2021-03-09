package com.netcracker.wizardapp.controller;


import com.netcracker.wizardapp.domain.Wizard;
import com.netcracker.wizardapp.repository.WizardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.header.Header;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    WizardRepo wizardRepo;
    @GetMapping("/all")
    public String allAccess() {
        System.out.println(SecurityContextHolder.getContext());
        return "Public Content.";
    }

    @GetMapping("/user")
    public String userAccess() {
        System.out.println(SecurityContextHolder.getContext());
        return "User Content.";
    }

    @GetMapping("/mod")
    public String moderatorAccess() {
        System.out.println(SecurityContextHolder.getContext());
        List<Wizard> wizards = wizardRepo.findAll();
        Wizard wizard = wizards.get(0);
        return wizard.toString();
    }

    @GetMapping("/admin")
    public String adminAccess() {
        System.out.println(SecurityContextHolder.getContext());
        return "Admin Board.";
    }
}

