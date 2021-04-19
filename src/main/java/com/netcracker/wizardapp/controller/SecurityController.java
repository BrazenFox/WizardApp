package com.netcracker.wizardapp.controller;

import com.netcracker.wizardapp.domain.Role;
import com.netcracker.wizardapp.domain.Roles;
import com.netcracker.wizardapp.domain.User;
import com.netcracker.wizardapp.exceptions.RoleNotFoundException;
import com.netcracker.wizardapp.payload.request.LoginRequest;
import com.netcracker.wizardapp.payload.request.RegistrationRequest;
import com.netcracker.wizardapp.payload.response.JwtResponse;
import com.netcracker.wizardapp.payload.response.MessageResponse;
import com.netcracker.wizardapp.repository.RoleRepo;
import com.netcracker.wizardapp.repository.UserRepo;
import com.netcracker.wizardapp.security.jwt.JwtUtils;
import com.netcracker.wizardapp.security.services.UserDetailsImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*@RestController
@RequestMapping("/security")
public class SecurityController {

}*/

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class SecurityController {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

   /* @Value("${Dspring.profiles.active}")
    private String myProperty;
    @Value("${Dspring.profiles.active1}")
    private String myProperty1;*/

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        /*logger.warn(myProperty + " " + myProperty1);*/

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        logger.info("User: \"" + SecurityContextHolder.getContext().getAuthentication().getName() + "\" with token: \"" + SecurityContextHolder.getContext().getAuthentication() + "\" is authorized");

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/logout")
    public void logout() {
        logger.info("User: \"" + SecurityContextHolder.getContext().getAuthentication().getName() + "\" with token: \"" + SecurityContextHolder.getContext().getAuthentication() + "\" has ended the session");
        SecurityContextHolder.clearContext();

    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest signUpRequest) {
        if (userRepo.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        System.out.println(strRoles);
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

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}

