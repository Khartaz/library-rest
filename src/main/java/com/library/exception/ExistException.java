package com.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExistException extends RuntimeException {

    public ExistException(String exception) {
        super(exception);
    }
}
