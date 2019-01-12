package com.library.exception;

public class ReaderNotFoundException extends RuntimeException {

    public ReaderNotFoundException(String exception) {
        super(exception);
    }
}
