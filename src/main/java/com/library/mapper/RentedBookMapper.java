package com.library.mapper;

import com.library.domain.RentedBook;
import com.library.domain.dto.RentedBookDto;
import org.springframework.stereotype.Component;

@Component
public class RentedBookMapper {

    public RentedBookDto mapToRentedBookDto(RentedBook rentedBook) {
        return new RentedBookDto(
                rentedBook.getBookId(),
                rentedBook.getReaderId(),
                rentedBook.getRentDate(),
                rentedBook.getReturnDate(),
                rentedBook.getNumberOfBooks(),
                rentedBook.isReturned(),
                rentedBook.getDateOfReturnBook()
        );
    }

    public RentedBook mapToRentedBook(RentedBookDto rentedBookDto) {
        return new RentedBook(
                rentedBookDto.getBookId(),
                rentedBookDto.getReaderId(),
                rentedBookDto.getRentDate(),
                rentedBookDto.getReturnDate(),
                rentedBookDto.getNumberOfBooks(),
                rentedBookDto.isReturned(),
                rentedBookDto.getDateOfReturnBook()

        );
    }
}
