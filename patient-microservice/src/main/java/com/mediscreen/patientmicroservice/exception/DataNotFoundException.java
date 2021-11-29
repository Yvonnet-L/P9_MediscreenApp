package com.mediscreen.patientmicroservice.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class DataNotFoundException extends RuntimeException{

    private static Logger logger = LogManager.getLogger(DataNotFoundException.class);

    public DataNotFoundException(String message) {
        super(message);
        logger.error("  **--> " + message);
    }
}
