package com.library.web.rest.controller;

import com.library.domain.dto.BookDto;
import com.library.domain.dto.ReaderDto;
import com.library.mapper.BookMapper;
import com.library.mapper.ReaderMapper;
import com.library.service.BookService;
import com.library.service.ReaderService;
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
    private ReaderMapper readerMapper;
    private ReaderService readerService;

    @Autowired
    public BookController(BookMapper bookMapper, BookService bookService,
                          ReaderMapper readerMapper, ReaderService readerService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
        this.readerMapper = readerMapper;
        this.readerService = readerService;
    }

    @PostMapping(value = "createBook", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody BookDto createBook(@RequestBody BookDto bookDto) {

        return bookMapper.mapToBookDto(bookService.createBook(bookDto));
    }

    @DeleteMapping(value = "deleteBook", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OperationStatus deleteBook(@RequestParam String bookId) {
        OperationStatus result = new OperationStatus();
        BookDto bookDto = bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));

        boolean status = bookService.deleteBook(bookDto);

        if (status) {
            result.setOperationName(ResponseOperationNames.DELETE.name());
            result.setOperationStatus(ResponseOperationStatus.SUCCESS.name());
        }
        return result;
    }

    @PutMapping(value = "addBookCopy", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto addBookCopy(@RequestParam String bookId, @RequestParam long quantity) {
        BookDto result = bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));

        return bookService.addBookCopy(result, quantity);
    }

    /*
    @PutMapping(value = "removeBookCopy", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto removeBookCopy(@RequestParam String bookId, @RequestParam long quantity) {
        BookDto result = bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));

        return bookService.removeBookCopy(result, quantity);
    }
    */
    @PutMapping(value = "destroyBook", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto destroyBook(@RequestParam String bookId, @RequestParam long quantity) {
        BookDto result = bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));

        return bookService.markAsDestroyed(result, quantity);
    }

    @PutMapping(value = "lostBook", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto lostBook(@RequestParam String bookId, @RequestParam long quantity) {
        BookDto bookDto = bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));
        String result = bookDto.getBookId();
        return bookService.markAsLost(result, quantity);
    }
    /*
    @PutMapping(value = "rentBook", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto rentBook(@RequestParam String bookId,  @RequestParam String readerId, @RequestParam long quantity) {
        BookDto bookDto = bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));

        ReaderDto readerDto = readerMapper.mapToReaderDto(readerService.getReaderByReaderId(readerId));

        return bookService.rentBook(bookDto, readerDto, quantity);
    }
    */
}
