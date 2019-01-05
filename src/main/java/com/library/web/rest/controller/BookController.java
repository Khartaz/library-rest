package com.library.web.rest.controller;

import com.library.domain.dto.BookDto;
import com.library.mapper.BookMapper;
import com.library.service.BookService;
import com.library.web.rest.response.OperationStatus;
import com.library.web.rest.response.ResponseOperationNames;
import com.library.web.rest.response.ResponseOperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/books")
public class BookController {
    private BookMapper bookMapper;
    private BookService bookService;

    @Autowired
    public BookController(BookMapper bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody BookDto createBook(@RequestBody BookDto bookDto) {
        return bookMapper.mapToBookDto(bookService.createBook(bookDto));
    }

    @DeleteMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OperationStatus deleteBook(@PathVariable String bookId) {
        OperationStatus result = new OperationStatus();
        BookDto bookDto = bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));

        boolean status = bookService.deleteBook(bookDto);

        if (status) {
            result.setOperationName(ResponseOperationNames.DELETE.name());
            result.setOperationStatus(ResponseOperationStatus.SUCCESS.name());
        }
        return result;
    }

    @PutMapping(value = "/copy/{bookId}/quantity/{quantity}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto addBookCopy(@PathVariable String bookId, @PathVariable long quantity) {
        BookDto result = bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));

        return bookService.addBookCopy(result, quantity);
    }

    @PutMapping(value = "/destroyed/{bookId}/quantity/{quantity}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto destroyBook(@PathVariable String bookId, @PathVariable long quantity) {
        BookDto result = bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));

        return bookService.markAsDestroyed(result, quantity);
    }

    @PutMapping(value = "/lost/{bookId}/quantity/{quantity}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto lostBook(@PathVariable String bookId, @PathVariable long quantity) {
        BookDto bookDto = bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));
        String result = bookDto.getBookId();
        return bookService.markAsLost(result, quantity);
    }

}
