package com.library.mapper;

import com.library.domain.Book;
import com.library.domain.BookCopies;
import com.library.domain.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    private BookCopiesMapper bookCopiesMapper;

    @Autowired
    public BookMapper(BookCopiesMapper bookCopiesMapper) {
        this.bookCopiesMapper = bookCopiesMapper;
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
                book.getBookCopies()
                        .stream()
                        .map(v -> bookCopiesMapper.mapToBookCopiesDto(v))
                        .collect(Collectors.toList()));
    }

    public Book mapToBook(BookDto bookDto, List<BookCopies> list) {
        Book book = mapToBook(bookDto);
        book.setBookCopies(list);

        return book;
    }

}
