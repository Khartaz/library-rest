package com.library.repository;

import com.library.domain.RentBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentBookRepository extends JpaRepository<RentBook, Long> {

    RentBook findRentBookByReaderIdAndBookIdAndReturnedIsFalse(String readerId, String bookId);

}
