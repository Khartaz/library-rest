package com.library.web.rest.controller;

import com.library.domain.dto.ReaderDto;
import com.library.exception.ReaderNotFoundException;
import com.library.facade.ReaderFacade;
import com.library.web.rest.response.OperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/readers")
public class ReaderController {
    private ReaderFacade facade;

    @Autowired
    public ReaderController(ReaderFacade facade) {
        this.facade = facade;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ReaderDto createNewReader(@RequestBody ReaderDto readerDto) {
        return facade.createNewReader(readerDto);
    }

    @PutMapping(value = "/{readerId}/rent/{bookId}/qty/{quantity}",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReaderDto rentBook(@PathVariable String readerId, @PathVariable String bookId, @PathVariable long quantity)  {
        return facade.rentBook(bookId, readerId, quantity);
    }

    @PutMapping(value = "/{readerId}/return/{bookId}/qty/{quantity}",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReaderDto returnBook(@PathVariable String readerId, @PathVariable String bookId, @PathVariable long quantity)  {
        return facade.returnBook(readerId, bookId, quantity);
    }

    @DeleteMapping(value = "/{readerId}",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OperationStatus deleteReader(@PathVariable String readerId) throws ReaderNotFoundException {
        return facade.deleteReader(readerId);
    }

    @GetMapping(value = "/{readerId}",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReaderDto getReaderByReaderId(@PathVariable String readerId)  {
        return facade.getReaderByReaderId(readerId);
    }
}
