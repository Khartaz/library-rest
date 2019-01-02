package com.library.mapper;

import com.library.domain.BookCopies;
import com.library.domain.dto.BookCopiesDto;
import org.springframework.stereotype.Component;

@Component
public class BookCopiesMapper {

    public BookCopiesDto mapToBookCopiesDto(BookCopies bookCopies) {
        return new BookCopiesDto(
                bookCopies.getBookId(),
                bookCopies.getTotal(),
                bookCopies.getRemainedInStock(),
                bookCopies.getRemainedToRent(),
                bookCopies.getRented(),
                bookCopies.getDestroyed(),
                bookCopies.getLost()
        );
    }

    public BookCopies mapToBookCopies(BookCopiesDto bookCopiesDto) {
        return new BookCopies(
                bookCopiesDto.getBookId(),
                bookCopiesDto.getTotal(),
                bookCopiesDto.getRemainedInStock(),
                bookCopiesDto.getRemainedToRent(),
                bookCopiesDto.getRented(),
                bookCopiesDto.getDestroyed(),
                bookCopiesDto.getLost()
        );
    }

}
