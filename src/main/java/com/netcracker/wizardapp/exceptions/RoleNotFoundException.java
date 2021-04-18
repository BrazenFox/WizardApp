package com.netcracker.wizardapp.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="No such Role")
public class RoleNotFoundException extends RuntimeException{
    private static final Logger logger = LogManager.getLogger();
    public RoleNotFoundException(String message) {
        super(message);
        logger.error(message);
    }
}
