package com.netcracker.wizardapp.service;

import com.netcracker.wizardapp.payload.request.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface SecurityService {
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
    void logout();
}
