package com.library.repository;

import com.library.domain.RentBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentBookRepository extends JpaRepository<RentBook, Long> {

    List<RentBook> findRentedBooksByReaderIdAndBookId(String readerId, String bookId);

    RentBook findRentedBookByReaderIdAndBookId(String readerId, String bookId);

    RentBook findRentBookByReaderIdAndBookIdAndReturnedIsFalse(String readerId, String bookId);

}
