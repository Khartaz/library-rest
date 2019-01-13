package com.library.exception.stock;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookStockException extends RuntimeException {

    public BookStockException(String exception) {
        super(exception);
    }
}
