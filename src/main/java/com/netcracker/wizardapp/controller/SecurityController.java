package com.netcracker.wizardapp.controller;

import com.netcracker.wizardapp.payload.request.LoginRequest;
import com.netcracker.wizardapp.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class SecurityController {


    @Autowired SecurityService securityService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return securityService.authenticateUser(loginRequest);
    }

    @PostMapping("/logout")
    public void logout() {
        securityService.logout();
    }
}

