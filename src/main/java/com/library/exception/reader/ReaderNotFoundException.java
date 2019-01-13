package com.library.exception.reader;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReaderNotFoundException extends RuntimeException implements Supplier {

    public ReaderNotFoundException(String exception) {
        super(exception);
    }

    /**
     * Gets a result.
     *
     * @return a result
     */
    @Override
    public Object get() {
        return null;
    }
}
