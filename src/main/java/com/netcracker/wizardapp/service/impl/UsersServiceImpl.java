package com.netcracker.wizardapp.service.impl;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsersServiceImpl implements UsersService {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private WizardRepo wizardRepo;
    @Override
    public ResponseEntity<User> findUser(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found with id: " + id));
        logger.info("User was found with id=" + user.getId());
        return ResponseEntity.ok(user);
    }

    @Override
    public Iterable<User> find() {
        return userRepo.findAll();
    }

    @Override
    public ResponseEntity<?> createUser(RegistrationRequest registrationRequest) {
        if (userRepo.existsByUsername(registrationRequest.getUsername())) {
            logger.error("Username: \"" + registrationRequest.getUsername() + "\" is already taken!");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        // Create new user's account
        User user = new User(registrationRequest.getUsername(),
                encoder.encode(registrationRequest.getPassword()));

        Set<String> strRoles = registrationRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepo.findByName(Roles.USER)
                    .orElseThrow(() -> new RoleNotFoundException("Error: Role " + Roles.USER + " is not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ADMIN":
                        Role adminRole = roleRepo.findByName((Roles.ADMIN))
                                .orElseThrow(() -> new RoleNotFoundException("Error: Role " + Roles.ADMIN + " is not found"));
                        roles.add(adminRole);

                        break;
                    case "MODERATOR":
                        Role modRole = roleRepo.findByName((Roles.MODERATOR))
                                .orElseThrow(() -> new RoleNotFoundException("Error: Role " + Roles.MODERATOR + " is not found"));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepo.findByName((Roles.USER))
                                .orElseThrow(() -> new RoleNotFoundException("Error: Role " + Roles.USER + " is not found"));
                        roles.add(userRole);
                }
            });
        }


        user.setRoles(roles);
        userRepo.save(user);
        logger.info(user.toString() + " was created");

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Override
    public ResponseEntity<?> updateUser(Long id, RegistrationRequest registrationRequest) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found with id: " + id));
        if (userRepo.existsByUsernameAndIdNot(registrationRequest.getUsername(), user.getId())) {
            logger.warn("Username: \"" + registrationRequest.getUsername() + "\" is already taken!");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(encoder.encode(registrationRequest.getPassword()));
        Set<String> strRoles = registrationRequest.getRoles();
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
        logger.info("User with id=" + user.getId() + " was updated");
        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }

    @Override
    public ResponseEntity<User> deleteUser(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found with id: " + id));
        logger.info("User was found with id=" + user.getId());
        List<Wizard> wizards = wizardRepo.findAllByCreator(user);
        wizardRepo.deleteAll(wizards);
        userRepo.delete(user);
        logger.info(user.toString() + " and his wizards have been removed");
        return ResponseEntity.ok(user);
    }
}
