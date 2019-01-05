package com.library.mapper;

import com.library.domain.RentBook;
import com.library.domain.dto.RentBookDto;
import org.springframework.stereotype.Component;

@Component
public class RentBookMapper {

    public RentBookDto mapToRentedBookDto(RentBook rentBook) {
        return new RentBookDto(
                rentBook.getBookId(),
                rentBook.getReaderId(),
                rentBook.getRentDate(),
                rentBook.getReturnDate(),
                rentBook.getNumberOfBooks(),
                rentBook.isReturned(),
                rentBook.getDateOfReturnBook()
        );
    }

    public RentBook mapToRentedBook(RentBookDto rentBookDto) {
        return new RentBook(
                rentBookDto.getBookId(),
                rentBookDto.getReaderId(),
                rentBookDto.getRentDate(),
                rentBookDto.getReturnDate(),
                rentBookDto.getNumberOfBooks(),
                rentBookDto.isReturned(),
                rentBookDto.getDateOfReturnBook()

        );
    }
}
