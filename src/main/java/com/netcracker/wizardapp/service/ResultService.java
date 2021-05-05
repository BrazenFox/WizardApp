package com.netcracker.wizardapp.service;

import com.netcracker.wizardapp.domain.Result;
import com.netcracker.wizardapp.payload.request.ResultRequest;
import org.springframework.http.ResponseEntity;

public interface ResultService {
    ResponseEntity<?> createResult(ResultRequest resultView);
    Iterable<Result> findResultAll();
    Iterable<Result> findResultforModer(Long id);
    Iterable<Result> findResultForUser(Long id);
}
