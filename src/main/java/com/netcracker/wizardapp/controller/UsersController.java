package com.netcracker.wizardapp.controller;

import com.netcracker.wizardapp.domain.User;
import com.netcracker.wizardapp.payload.request.RegistrationRequest;
import com.netcracker.wizardapp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
