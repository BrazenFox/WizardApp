package com.netcracker.wizardapp.controller;

import com.netcracker.wizardapp.domain.Role;
import com.netcracker.wizardapp.domain.Roles;
import com.netcracker.wizardapp.domain.User;
import com.netcracker.wizardapp.domain.Wizard;
import com.netcracker.wizardapp.exceptions.RoleNotFoundException;
import com.netcracker.wizardapp.exceptions.UserNotFoundException;
import com.netcracker.wizardapp.payload.request.RegistrationRequest;
import com.netcracker.wizardapp.payload.response.MessageResponse;
import com.netcracker.wizardapp.repository.RoleRepo;
import com.netcracker.wizardapp.repository.UserRepo;
import com.netcracker.wizardapp.repository.WizardRepo;
import com.netcracker.wizardapp.service.UsersService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UsersController {
    @Autowired
    UsersService usersService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody RegistrationRequest registrationRequest) {
        return usersService.createUser(registrationRequest);
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<User> findUser(@PathVariable(value = "id") Long id) {
        return usersService.findUser(id);
    }

    @GetMapping("/find")
    public Iterable<User> find() {
        return usersService.find();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") Long id) {
        return usersService.deleteUser(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") Long id, @RequestBody RegistrationRequest registrationRequest) {
        return usersService.updateUser(id,registrationRequest);
    }
}
