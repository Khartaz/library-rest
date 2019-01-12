package com.library.exception.rent;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RentBookException extends RuntimeException {

    public RentBookException(String exception) {
        super(exception);
    }
}
