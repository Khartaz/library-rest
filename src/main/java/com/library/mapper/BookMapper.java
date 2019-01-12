package com.library.mapper;

import com.library.domain.Book;
import com.library.domain.BookStock;
import com.library.domain.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    private BookStockMapper bookStockMapper;

    @Autowired
    public BookMapper(BookStockMapper bookStockMapper) {
        this.bookStockMapper = bookStockMapper;
    }

    public Book mapToBook(BookDto bookDto) {
        return new Book(
                bookDto.getBookId(),
                bookDto.getTitle(),
                bookDto.getAuthor(),
                bookDto.getYearOfPublication());
    }

    public BookDto mapToBookDto(Book book) {
        return new BookDto(
                book.getBookId(),
                book.getAuthor(),
                book.getTitle(),
                book.getYearOfPublication(),
                book.getBookStock()
                        .stream()
                        .map(v -> bookStockMapper.mapToBookCopiesDto(v))
                        .collect(Collectors.toList()));
    }

    public Book mapToBook(BookDto bookDto, List<BookStock> list) {
        Book book = mapToBook(bookDto);
        book.setBookStock(list);

        return book;
    }
}
