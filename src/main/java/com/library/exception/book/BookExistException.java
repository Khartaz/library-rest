package com.library.exception.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookExistException extends RuntimeException {

    public BookExistException(String exception) {
        super(exception);
    }
}
