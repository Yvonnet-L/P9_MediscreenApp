package com.mediscreen.historymicroservice.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataNotConformException extends RuntimeException{

    private static Logger logger = LogManager.getLogger(DataNotConformException.class);

    public DataNotConformException(String message) {
        super(message);
        logger.error("  **--> " + message);
    }

}
