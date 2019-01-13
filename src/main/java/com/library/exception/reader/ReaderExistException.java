package com.library.exception.reader;

import com.library.exception.ExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReaderExistException extends ExistException {

    public ReaderExistException(String exception) {
        super(exception);
    }
}
