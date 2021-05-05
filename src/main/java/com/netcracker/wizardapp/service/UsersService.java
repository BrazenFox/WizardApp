package com.netcracker.wizardapp.service;

import com.netcracker.wizardapp.domain.User;
import com.netcracker.wizardapp.payload.request.RegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UsersService {
    ResponseEntity<User> findUser(Long id);
    Iterable<User> find();
    ResponseEntity<?> createUser(RegistrationRequest registrationRequest);
    ResponseEntity<?> updateUser(Long id, RegistrationRequest registrationRequest);
    ResponseEntity<User> deleteUser(Long id);



}
