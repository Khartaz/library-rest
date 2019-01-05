package com.library.service;

import com.library.domain.Book;
import com.library.domain.Reader;
import com.library.domain.RentedBook;
import com.library.domain.dto.BookDto;
import com.library.domain.dto.ReaderDto;
import com.library.expeption.*;
import com.library.mapper.ReaderMapper;
import com.library.repository.ReaderRepository;
import com.library.repository.RentedBookRepository;
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
public class ReaderService {
    private ReaderRepository repository;
    private ReaderMapper mapper;
    private Utils utils;
    private BookService bookService;
    private RentedBookRepository rentedBookRepository;

    @Autowired
    public ReaderService(ReaderRepository repository, ReaderMapper mapper, Utils utils,
                         BookService bookService, RentedBookRepository rentedBookRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.utils = utils;
        this.bookService = bookService;
        this.rentedBookRepository = rentedBookRepository;
    }

    public Reader createReader(ReaderDto readerDto)   {
        try {
            checkReaderId(mapper.mapToReader(readerDto));
        } catch (ReaderException e) {
            //
        }
        String readerId = utils.generateId(5);

        readerDto.setReaderId(readerId);
        readerDto.setCreated(utils.createDate());

        return repository.save(mapper.mapToReader(readerDto));
    }

    private Reader checkReaderId(Reader reader) throws ReaderException {
        try {
            if (repository.findReaderByReaderId(reader.getReaderId()) != null) {
                throw new ReaderException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
            }
        } catch (NullPointerException e) {
            //
        }
        return reader;
    }

    public Reader getReaderByReaderId(String readerId) throws NullPointerException {
        Reader reader = repository.findReaderByReaderId(readerId);

        if (reader == null) {
            throw new NullPointerException(ReaderMessages.READER_NOT_FOUND.getErrorMessage());
        }
        return reader;
    }

    public boolean checkRentStatus(String bookId, String readerId) {
        List<RentedBook> books = rentedBookRepository.findRentedBooksByReaderIdAndBookId(readerId, bookId);

        return books.stream().anyMatch(RentedBook::isReturned);
    }


    public ReaderDto rentBook(BookDto bookDto, ReaderDto readerDto, long quantity) throws BookException {
        Reader reader = getReaderByReaderId(readerDto.getReaderId());

        boolean isReturned = checkRentStatus(bookDto.getBookId(), reader.getReaderId());
        if (!isReturned) {
            throw new BookException(BookMessages.BOOK_RENTED.getErrorMessage());
        }

        Book book = bookService.markAsRented(bookDto, quantity);

        String bookId = book.getBookId();

        List<RentedBook> list = new ArrayList<>();

        RentedBook rentedBook = new RentedBook();

        rentedBook.setBookId(bookId);
        rentedBook.setReaderId(reader.getReaderId());
        rentedBook.setRentDate(new Date());
        rentedBook.setReturnDate(utils.rentedDays(14));
        rentedBook.setNumberOfBooks(quantity);

        list.add(rentedBook);
        reader.setRentedBooks(list);

        repository.save(reader);

        return mapper.mapToReaderDto(reader);
    }

    @SuppressWarnings("Duplicates")
    public ReaderDto returnBook(ReaderDto readerDto, String bookId, long quantity) throws BookException {
        Reader reader = getReaderByReaderId(readerDto.getReaderId());

        boolean isReturned = checkRentStatus(bookId, reader.getReaderId());
        if (isReturned) {
            throw new BookException(BookMessages.RENT_FIRST.getErrorMessage());
        }

        String readerId = reader.getReaderId();

        RentedBook rentedBook = rentedBookRepository.findRentedBookByReaderIdAndBookId(readerId, bookId);


        long booksInStock = Optional.of(rentedBook.getNumberOfBooks()).orElseThrow(NullPointerException::new);

        if(booksInStock > quantity) {
            long booksLost = booksInStock - quantity;

            bookService.markAsLost(bookId, booksLost);

            rentedBook.setDateOfReturnBook(new Date());
            rentedBook.setReturned(true);
            rentedBook.setNumberOfBooks(0);

            bookService.markAsReturn(bookId, booksInStock);

            repository.save(reader);

            return mapper.mapToReaderDto(reader);

        } else if (booksInStock < quantity) {
            throw new NullPointerException(BookMessages.BOOKS_TO_LESS.getErrorMessage());
        }

        rentedBook.setDateOfReturnBook(new Date());
        rentedBook.setReturned(true);
        rentedBook.setNumberOfBooks(0);

        bookService.markAsReturn(bookId, quantity);

        repository.save(reader);

        return mapper.mapToReaderDto(reader);
    }
}
