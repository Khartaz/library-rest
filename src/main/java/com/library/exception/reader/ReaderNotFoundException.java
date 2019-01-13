package com.library.exception.reader;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReaderNotFoundException extends RuntimeException {

    public ReaderNotFoundException(String exception) {
        super(exception);
    }

}
