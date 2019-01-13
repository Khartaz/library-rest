package com.library.exception.reader;

import com.library.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReaderNotFoundException extends NotFoundException {

    public ReaderNotFoundException(String exception) {
        super(exception);
    }

}
