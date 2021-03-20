package com.netcracker.wizardapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="No such Wizard")
public class ResourceNotFoundException extends RuntimeException{
}
