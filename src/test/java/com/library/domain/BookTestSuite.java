package com.library.domain;

import com.library.repository.BookRepository;
import com.library.repository.ReaderRepository;
import com.library.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Transactional
@Rollback(false)
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookTestSuite {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private Utils utils;

    @Test
    public void testBookWithCopiesSave() {
        //Given
        Book book = new Book("test", "test", new Date());
        book.setBookId("bookUniqId2");

        List<BookStock> bookStockList = new ArrayList<>();
        BookStock bookStock = new BookStock(10,7, 1, 1, 1);
        bookStock.setBookId(book.getBookId());
        bookStockList.add(bookStock);
        book.setBookStock(bookStockList);
        //When
        bookRepository.save(book);
        //Then
        long id = book.getId();
        Book bookId = bookRepository.findOne(id);
        assertEquals(id, book.getId());
        //CleanUp
        bookRepository.delete(bookId);
    }

    @Test
    public void testBookWithCopiesAndRentedAndReaderSave() {
        //Given
        Book book = new Book("TestTitle", "TestAuthor", new Date());
        book.setBookId("BookUniqId");

        List<BookStock> bookStockList = new ArrayList<>();
        BookStock bookStock = new BookStock(10,7, 1, 1, 1);
        bookStock.setBookId(book.getBookId());
        bookStockList.add(bookStock);
        book.setBookStock(bookStockList);

        Reader reader = new Reader("TestName", "TestLastName", new Date());
        reader.setReaderId("ReaderUniqueIdTest");

        Date dateAfterTwoWeeks = utils.rentedDays(14);

        List<RentBook> rentBookList = new ArrayList<>();
        RentBook rentBook = new RentBook(new Date(), dateAfterTwoWeeks);
        rentBook.setBookId(book.getBookId());
        rentBook.setReaderId(reader.getReaderId());
        rentBookList.add(rentBook);
        reader.setRentBooks(rentBookList);
        //When
        bookRepository.save(book);
        readerRepository.save(reader);
        //Then
        long bookId = book.getId();
        long readerId = reader.getId();
        assertEquals(bookId, book.getId());
        assertEquals(readerId, reader.getId());
        //CleanUp
        bookRepository.delete(bookId);
        readerRepository.delete(readerId);
    }
}
