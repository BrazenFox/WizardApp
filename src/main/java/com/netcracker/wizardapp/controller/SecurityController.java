package com.netcracker.wizardapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {
    @GetMapping("/for_user")
    public String forUser(){
        return "users page";
    }
    @GetMapping("/for_admin")
    public String forAdmin(){
        return "admins page";
    }
}
