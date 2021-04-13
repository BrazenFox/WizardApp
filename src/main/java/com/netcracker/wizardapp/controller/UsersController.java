package com.netcracker.wizardapp.controller;

import com.netcracker.wizardapp.domain.Role;
import com.netcracker.wizardapp.domain.Roles;
import com.netcracker.wizardapp.domain.User;
import com.netcracker.wizardapp.domain.Wizard;
import com.netcracker.wizardapp.payload.request.RegistrationRequest;
import com.netcracker.wizardapp.payload.response.MessageResponse;
import com.netcracker.wizardapp.repository.RoleRepo;
import com.netcracker.wizardapp.repository.UserRepo;
import com.netcracker.wizardapp.repository.WizardRepo;
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
    PasswordEncoder encoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private WizardRepo wizardRepo;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody RegistrationRequest registrationRequest) {
        if (userRepo.existsByUsername(registrationRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        // Create new user's account
        User user = new User(registrationRequest.getUsername(),
                encoder.encode(registrationRequest.getPassword()));

        Set<String> strRoles = registrationRequest.getRoles();
        System.out.println(strRoles);
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepo.findByName(Roles.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ADMIN":
                        Role adminRole = roleRepo.findByName((Roles.ADMIN))
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "MODERATOR":
                        Role modRole = roleRepo.findByName((Roles.MODERATOR))
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepo.findByName((Roles.USER))
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }


        user.setRoles(roles);
        userRepo.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }


    @GetMapping("/find/{id}")
    public ResponseEntity<User> findUser(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id)));
    }

    @GetMapping("/find")
    public Iterable<User> find() {
        return userRepo.findAll();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
        List<Wizard> wizards = wizardRepo.findAllByCreator(user);
        wizardRepo.deleteAll(wizards);
        userRepo.delete(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @RequestBody RegistrationRequest registrationRequest){
        User user = userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(registrationRequest.getPassword());
        Set<String> strRoles = registrationRequest.getRoles();
        System.out.println(strRoles);
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepo.findByName(Roles.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ADMIN":
                        Role adminRole = roleRepo.findByName((Roles.ADMIN))
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "MODERATOR":
                        Role modRole = roleRepo.findByName((Roles.MODERATOR))
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepo.findByName((Roles.USER))
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        User updateUser = userRepo.save(user);
        return ResponseEntity.ok(updateUser);
    }
}
