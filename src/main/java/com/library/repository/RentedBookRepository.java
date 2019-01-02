package com.library.repository;

import com.library.domain.RentedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentedBookRepository extends JpaRepository<RentedBook, Long> {

    List<RentedBook> findRentedBooksByReaderIdAndBookId(String readerId, String bookId);

    RentedBook findRentedBookByReaderIdAndBookId(String readerId, String bookId);

}
