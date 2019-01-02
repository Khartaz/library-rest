package com.library.web.rest.controller;

import com.library.domain.dto.BookDto;
import com.library.domain.dto.ReaderDto;
import com.library.expeption.BookException;
import com.library.mapper.BookMapper;
import com.library.mapper.ReaderMapper;
import com.library.service.BookService;
import com.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/readers")
public class ReaderController {
    private ReaderMapper mapper;
    private ReaderService service;
    private BookService bookService;
    private BookMapper bookMapper;

    @Autowired
    public ReaderController(ReaderMapper mapper, ReaderService service,
                            BookService bookService, BookMapper bookMapper) {
        this.mapper = mapper;
        this.service = service;
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping(value = "createReader", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ReaderDto createReader(@RequestBody ReaderDto readerDto)  {
        return mapper.mapToReaderDto(service.createReader(readerDto));
    }

    @PutMapping(value = "rentBook", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReaderDto rentBook(@RequestParam String bookId, @RequestParam String readerId, @RequestParam long quantity) throws BookException {
        BookDto bookDto = bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));

        ReaderDto readerDto = mapper.mapToReaderDto(service.getReaderByReaderId(readerId));

        return service.rentBook(bookDto, readerDto, quantity);
    }

    @PutMapping(value = "returnBook", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReaderDto returnBook(@RequestParam String readerId, @RequestParam String bookId, @RequestParam long quantity)  {
        ReaderDto readerDto = mapper.mapToReaderDto(service.getReaderByReaderId(readerId));

        return service.returnBook(readerDto, bookId, quantity);
    }
}
