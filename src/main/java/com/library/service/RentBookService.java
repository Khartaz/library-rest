package com.library.service;

import com.library.domain.Book;
import com.library.domain.Reader;
import com.library.domain.RentBook;
import com.library.domain.dto.BookDto;
import com.library.domain.dto.ReaderDto;
import com.library.expeption.BookException;
import com.library.expeption.BookMessages;
import com.library.mapper.ReaderMapper;
import com.library.repository.ReaderRepository;
import com.library.repository.RentBookRepository;
import com.library.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RentBookService {
    private BookService bookService;
    private RentBookRepository rentBookRepository;
    private ReaderService readerService;
    private ReaderMapper readerMapper;
    private Utils utils;
    private ReaderRepository readerRepository;

    @Autowired
    public RentBookService(BookService bookService, RentBookRepository rentBookRepository,
                           ReaderService readerService, ReaderMapper readerMapper,
                           Utils utils, ReaderRepository readerRepository) {
        this.bookService = bookService;
        this.rentBookRepository = rentBookRepository;
        this.readerService = readerService;
        this.readerMapper = readerMapper;
        this.utils = utils;
        this.readerRepository = readerRepository;
    }

    public boolean checkRentStatus(String bookId, String readerId) {
        RentBook book = rentBookRepository.findRentBookByReaderIdAndBookIdAndReturnedIsFalse(readerId, bookId);

        return book == null;
    }


    public ReaderDto rentBook(BookDto bookDto, ReaderDto readerDto, long quantity) throws BookException {
        Reader reader = readerService.getReaderByReaderId(readerDto.getReaderId());

        boolean isReturned = checkRentStatus(bookDto.getBookId(), reader.getReaderId());
        if (!isReturned) {
            throw new BookException(BookMessages.BOOK_RENTED.getErrorMessage());
        }

        Book book = bookService.markAsRented(bookDto, quantity);

        String bookId = book.getBookId();

        List<RentBook> list = new ArrayList<>();

        RentBook rentBook = new RentBook();

        rentBook.setBookId(bookId);
        rentBook.setReaderId(reader.getReaderId());
        rentBook.setRentDate(new Date());
        rentBook.setReturnDate(utils.rentedDays(14));
        rentBook.setNumberOfBooks(quantity);

        list.add(rentBook);
        reader.setRentBooks(list);

        readerRepository.save(reader);

        return readerMapper.mapToReaderDto(reader);
    }

    public ReaderDto returnBook(ReaderDto readerDto, String bookId, long quantity) throws BookException {
        Reader reader = readerService.getReaderByReaderId(readerDto.getReaderId());
        /*
        boolean isReturned = checkRentStatus(bookId, reader.getReaderId());
        if (!isReturned) {
            throw new BookException(BookMessages.RENT_FIRST.getErrorMessage());
        }
        */
        String readerId = reader.getReaderId();

        RentBook rentedBook = rentBookRepository.findRentBookByReaderIdAndBookIdAndReturnedIsFalse(readerId, bookId);


        long booksInStock = Optional.of(rentedBook.getNumberOfBooks()).orElseThrow(NullPointerException::new);

        if(booksInStock > quantity) {
            long booksLost = booksInStock - quantity;

            bookService.markAsLost(bookId, booksLost);

            rentedBook.setDateOfReturnBook(new Date());
            rentedBook.setReturned(true);
            rentedBook.setNumberOfBooks(0);

            bookService.markAsReturn(bookId, booksInStock);

            readerRepository.save(reader);

            return readerMapper.mapToReaderDto(reader);

        } else if (booksInStock < quantity) {
            throw new NullPointerException(BookMessages.BOOKS_TO_LESS.getErrorMessage());
        }

        rentedBook.setDateOfReturnBook(new Date());
        rentedBook.setReturned(true);
        rentedBook.setNumberOfBooks(0);

        bookService.markAsReturn(bookId, quantity);

        readerRepository.save(reader);

        return readerMapper.mapToReaderDto(reader);
    }
}
