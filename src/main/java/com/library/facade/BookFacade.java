package com.library.facade;

import com.library.domain.dto.BookDto;
import com.library.mapper.BookMapper;
import com.library.service.BookService;
import com.library.web.rest.response.OperationStatus;
import com.library.web.rest.response.ResponseOperationNames;
import com.library.web.rest.response.ResponseOperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookFacade {
    private BookMapper bookMapper;
    private BookService bookService;

    @Autowired
    public BookFacade(BookMapper bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    public BookDto createNewBook(BookDto bookDto) {
        return bookMapper.mapToBookDto(bookService.createBook(bookDto));
    }

    public OperationStatus deleteBook(String bookId) {
        OperationStatus result = new OperationStatus();
        BookDto bookDto = bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));

        boolean status = bookService.deleteBook(bookDto);

        if (status) {
            result.setOperationName(ResponseOperationNames.DELETE.name());
            result.setOperationStatus(ResponseOperationStatus.SUCCESS.name());
        }
        return result;
    }

    public BookDto addBooksToStock(String bookId, long quantity) {
        BookDto result = bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));

        return bookService.addBookCopy(result, quantity);
    }

    public BookDto destroyedBook(String bookId, long quantity) {
        BookDto result = bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));

        return bookService.markAsDestroyed(result, quantity);
    }

    public BookDto lostBook(String bookId, long quantity) {
        BookDto bookDto = bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));
        String result = bookDto.getBookId();
        return bookService.markAsLost(result, quantity);
    }

    public BookDto getBookByBookId(String bookId) {
       return bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));
    }
}
