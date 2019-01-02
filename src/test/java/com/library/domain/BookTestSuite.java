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

        List<BookCopies> bookCopiesList = new ArrayList<>();
        BookCopies bookCopies = new BookCopies(10,7, 1, 1, 1);
        bookCopies.setBookId(book.getBookId());
        bookCopiesList.add(bookCopies);
        book.setBookCopies(bookCopiesList);
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

        List<BookCopies> bookCopiesList = new ArrayList<>();
        BookCopies bookCopies = new BookCopies(10,7, 1, 1, 1);
        bookCopies.setBookId(book.getBookId());
        bookCopiesList.add(bookCopies);
        book.setBookCopies(bookCopiesList);

        Reader reader = new Reader("TestName", "TestLastName", new Date());
        reader.setReaderId("ReaderUniqueIdTest");

        Date dateAfterTwoWeeks = utils.rentedDays(14);

        List<RentedBook> rentedBookList = new ArrayList<>();
        RentedBook rentedBook = new RentedBook(new Date(), dateAfterTwoWeeks);
        rentedBook.setBookId(book.getBookId());
        rentedBook.setReaderId(reader.getReaderId());
        rentedBookList.add(rentedBook);
        reader.setRentedBooks(rentedBookList);
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
