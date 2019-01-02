package com.library.service;

import com.library.domain.Book;
import com.library.domain.BookCopies;
import com.library.domain.dto.BookDto;
import com.library.expeption.ErrorMessages;
import com.library.mapper.BookCopiesMapper;
import com.library.mapper.BookMapper;
import com.library.repository.BookRepository;
import com.library.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {
    private BookRepository bookRepository;
    private BookMapper mapper;
    private BookCopiesMapper copiesMapper;
    private Utils utils;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper mapper,
                       BookCopiesMapper copiesMapper, Utils utils) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
        this.copiesMapper = copiesMapper;
        this.utils = utils;
    }

    public Book createBook(BookDto bookDto) {
        checkBookId(mapper.mapToBook(bookDto));
        String bookId = utils.generateId(8);

        bookDto.setBookId(bookId);

        List<BookCopies> list = bookDto.getBookCopies()
                .stream()
                .map(v -> copiesMapper.mapToBookCopies(v))
                .collect(Collectors.toList());

        list.forEach(v -> v.setBookId(bookId));

        return bookRepository.save(mapper.mapToBook(bookDto, list));
    }

    private Book checkBookId(Book book) throws RuntimeException {
        if (bookRepository.findBookByBookId(book.getBookId()) != null) {
            throw new RuntimeException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }
        return book;
    }

    public Book getBookByBookId(String bookId) throws NullPointerException {
        Book book = bookRepository.findBookByBookId(bookId);

        if (book == null) {
            throw new NullPointerException(ErrorMessages.STOCK_IS_EMPTY.getErrorMessage());
        }
        return book;
    }

    public boolean deleteBook(BookDto bookDto) {
        Book book = getBookByBookId(bookDto.getBookId());
        long id = book.getId();

        bookRepository.delete(id);

        return true;
    }

    @SuppressWarnings("Duplicates")
    public BookDto addBookCopy(BookDto bookDto, long quantity) {
        Book book = getBookByBookId(bookDto.getBookId());

        long booksTotal = book.getBookCopies()
                .stream()
                .map(BookCopies::getTotal)
                .reduce(quantity, Long::sum);

        long booksRemainedToRent = book.getBookCopies()
                .stream()
                .map(BookCopies::getRemainedToRent)
                .reduce(quantity, Long::sum);

        long booksRemainedInStock = book.getBookCopies()
                .stream()
                .map(BookCopies::getRemainedInStock)
                .reduce(quantity, Long::sum);

        book.getBookCopies().forEach(v -> v.setTotal(booksTotal));
        book.getBookCopies().forEach(v -> v.setRemainedToRent(booksRemainedToRent));
        book.getBookCopies().forEach(v -> v.setRemainedInStock(booksRemainedInStock));

        bookRepository.save(book);

        return mapper.mapToBookDto(book);
    }

    @SuppressWarnings("Duplicates")
    private BookDto removeBookCopy(BookDto bookDto, long quantity) throws ArithmeticException {
        Book book = getBookByBookId(bookDto.getBookId());

        long booksTotal = book.getBookCopies()
                .stream()
                .map(BookCopies::getRemainedInStock)
                .mapToLong(v -> v - quantity)
                .sum();

        if (booksTotal < 0 ) {
            throw new ArithmeticException(ErrorMessages.STOCK_IS_EMPTY.getErrorMessage());
        }

        book.getBookCopies().forEach(v -> v.setRemainedInStock(booksTotal));

        bookRepository.save(book);

        return mapper.mapToBookDto(book);
    }

    @SuppressWarnings("Duplicates")
    public BookDto markAsDestroyed(BookDto bookDto, long quantity) throws ArithmeticException {
        Book book = getBookByBookId(bookDto.getBookId());

        removeBookCopy(mapper.mapToBookDto(book), quantity);

        long remainedBooks = book.getBookCopies()
                .stream()
                .map(BookCopies::getRemainedToRent)
                .mapToLong(v -> v - quantity)
                .sum();

        long booksDestroyed = book.getBookCopies()
                .stream()
                .map(BookCopies::getDestroyed)
                .reduce(quantity, Long::sum);

        if (booksDestroyed < 0) {
            throw new ArithmeticException(ErrorMessages.STOCK_IS_EMPTY.getErrorMessage());
        }

        book.getBookCopies().forEach(v -> v.setRemainedToRent(remainedBooks));
        book.getBookCopies().forEach(v -> v.setDestroyed(booksDestroyed));

        bookRepository.save(book);

        return mapper.mapToBookDto(book);
    }

    @SuppressWarnings("Duplicates")
    public BookDto markAsLost(String bookId, long quantity) throws ArithmeticException {
        Book book = getBookByBookId(bookId);

        removeBookCopy(mapper.mapToBookDto(book), quantity);

        long remainedBooks = book.getBookCopies()
                .stream()
                .map(BookCopies::getRemainedToRent)
                .mapToLong(v -> v - quantity)
                .sum();

        long booksLost = book.getBookCopies()
                .stream()
                .map(BookCopies::getLost)
                .reduce(quantity, Long::sum);

        if (booksLost < 0) {
            throw new ArithmeticException(ErrorMessages.STOCK_IS_EMPTY.getErrorMessage());
        }

        book.getBookCopies().forEach(v -> v.setRemainedToRent(remainedBooks));
        book.getBookCopies().forEach(v -> v.setLost(booksLost));

        bookRepository.save(book);

        return mapper.mapToBookDto(book);
    }

    @SuppressWarnings("Duplicates")
    public Book markAsRented(BookDto bookDto, long quantity) throws ArithmeticException {
        Book book = getBookByBookId(bookDto.getBookId());

        long remainedBooks = book.getBookCopies()
                .stream()
                .map(BookCopies::getRemainedToRent)
                .mapToLong(v -> v - quantity)
                .sum();

        long booksRented = book.getBookCopies()
                .stream()
                .map(BookCopies::getRented)
                .reduce(quantity, Long::sum);

        if (remainedBooks < 0 || booksRented < 0) {
            throw new ArithmeticException(ErrorMessages.STOCK_IS_EMPTY.getErrorMessage());
        }
        book.getBookCopies().forEach(v -> v.setRemainedToRent(remainedBooks));
        book.getBookCopies().forEach(v -> v.setRented(booksRented));

        bookRepository.save(book);

        return book;
    }

    @SuppressWarnings("Duplicates")
    public Book markAsReturn(String bookId, long quantity) {
        Book book = getBookByBookId(bookId);

        long remainedBooks = book.getBookCopies()
                .stream()
                .map(BookCopies::getRemainedToRent)
                .reduce(quantity, Long::sum);

        long booksRented = book.getBookCopies()
                .stream()
                .map(BookCopies::getRented)
                .mapToLong(v -> v - quantity)
                .sum();

        book.getBookCopies().forEach(v -> v.setRemainedToRent(remainedBooks));
        book.getBookCopies().forEach(v -> v.setRented(booksRented));

        bookRepository.save(book);

        return book;
    }

}
