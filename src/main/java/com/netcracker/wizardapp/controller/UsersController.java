package com.netcracker.wizardapp.controller;

import com.netcracker.wizardapp.domain.Role;
import com.netcracker.wizardapp.domain.Roles;
import com.netcracker.wizardapp.domain.User;
import com.netcracker.wizardapp.repository.RoleRepo;
import com.netcracker.wizardapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @PostMapping("/adduser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        Set<Role> roles = new HashSet<>();
        Role adminRole = roleRepo.findByName(Roles.USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(adminRole);;
        user.setRoles(roles);
        userRepo.save(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/addmoderator")
    public ResponseEntity<User> addModerator(@RequestBody User user) {
        Set<Role> roles = new HashSet<>();
        Role adminRole = roleRepo.findByName(Roles.MODERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(adminRole);;
        user.setRoles(roles);
        userRepo.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<?> findUser(@PathVariable(value = "username") String username) {
        //User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username))
        return ResponseEntity.ok(userRepo.findByUsername(username));

    }

    @GetMapping("/find")
    public Iterable<User> find() {
        return userRepo.findAll();
    }


    @DeleteMapping("/delete/{username}")
    public User deleteWaizard(@PathVariable(value = "username") String username) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        userRepo.delete(user);
        return user;
    }
}
