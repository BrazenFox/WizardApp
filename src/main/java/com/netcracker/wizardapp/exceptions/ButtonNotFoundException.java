package com.netcracker.wizardapp.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="No such Button")
public class ButtonNotFoundException extends  RuntimeException{
    private static final Logger logger = LogManager.getLogger();
    public ButtonNotFoundException(String message) {
        super(message);
        logger.error(message);
    }
}
