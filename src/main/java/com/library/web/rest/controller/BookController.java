package com.library.web.rest.controller;

import com.library.domain.dto.BookDto;
import com.library.facade.BookFacade;
import com.library.web.rest.response.OperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/books")
public class BookController {
    private BookFacade facade;

    @Autowired
    public BookController(BookFacade facade) {
        this.facade = facade;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody BookDto createNewBook(@RequestBody BookDto bookDto) {
        return facade.createNewBook(bookDto);
    }

    @DeleteMapping(value = "/{bookId}",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OperationStatus deleteBook(@PathVariable String bookId) {
        return facade.deleteBook(bookId);
    }

    @PutMapping(value = "/copy/{bookId}/qty/{quantity}",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto addBooksToStock(@PathVariable String bookId, @PathVariable long quantity) {
        return facade.addBooksToStock(bookId, quantity);
    }

    @PutMapping(value = "/destroyed/{bookId}/qty/{quantity}",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto destroyedBook(@PathVariable String bookId, @PathVariable long quantity) {
        return facade.destroyedBook(bookId, quantity);
    }

    @PutMapping(value = "/lost/{bookId}/qty/{quantity}",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto lostBook(@PathVariable String bookId, @PathVariable long quantity) {
        return facade.lostBook(bookId, quantity);
    }

}
