package com.library.exception.rent;

import com.library.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RentBookNotFoundException extends NotFoundException {

    public RentBookNotFoundException(String exception) {
        super(exception);
    }
}
