package com.library.service;

import com.library.domain.Book;
import com.library.domain.BookStock;
import com.library.domain.dto.BookDto;
import com.library.exception.BookNotFoundException;
import com.library.exception.ErrorMessages;
import com.library.mapper.BookStockMapper;
import com.library.mapper.BookMapper;
import com.library.repository.BookRepository;
import com.library.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {
    private BookRepository bookRepository;
    private BookMapper mapper;
    private BookStockMapper copiesMapper;
    private Utils utils;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper mapper,
                       BookStockMapper copiesMapper, Utils utils) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
        this.copiesMapper = copiesMapper;
        this.utils = utils;
    }

    public Book createBook(BookDto bookDto) throws BookNotFoundException {
        if (!checkBookId(bookDto.getBookId())) {
            throw new BookNotFoundException(bookDto.getBookId());
        }
        String bookId = utils.generateId(8);

        bookDto.setBookId(bookId);

        List<BookStock> list = bookDto.getBookCopies()
                .stream()
                .map(v -> copiesMapper.mapToBookCopies(v))
                .collect(Collectors.toList());

        list.forEach(v -> v.setBookId(bookId));

        return bookRepository.save(mapper.mapToBook(bookDto, list));
    }

    public Optional<Book> getBookByBookId(String bookId) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findBookByBookId(bookId);

        if (!book.isPresent()) {
            throw new BookNotFoundException("Id is " + bookId);
        }
        return book;
    }

    public boolean checkBookId(String bookId) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findBookByBookId(bookId);
        if (book.isPresent()) {
            throw new BookNotFoundException(ErrorMessages.STOCK_IS_EMPTY.getErrorMessage());
        }
        return true;
    }

    public boolean deleteBook(BookDto bookDto) {
        Book book = getBookByBookId(bookDto.getBookId()).get();

        long id = book.getId();
        bookRepository.delete(id);

        return true;
    }

    @SuppressWarnings("Duplicates")
    public BookDto addBookCopy(BookDto bookDto, long quantity) throws ArithmeticException  {
        Book book = getBookByBookId(bookDto.getBookId()).get();

        long booksTotal = book.getBookStock()
                .stream()
                .map(BookStock::getTotal)
                .reduce(quantity, Long::sum);

        long booksRemainedToRent = book.getBookStock()
                .stream()
                .map(BookStock::getRemainedToRent)
                .reduce(quantity, Long::sum);

        long booksRemainedInStock = book.getBookStock()
                .stream()
                .map(BookStock::getRemainedInStock)
                .reduce(quantity, Long::sum);

        book.getBookStock().forEach(v -> v.setTotal(booksTotal));
        book.getBookStock().forEach(v -> v.setRemainedToRent(booksRemainedToRent));
        book.getBookStock().forEach(v -> v.setRemainedInStock(booksRemainedInStock));

        bookRepository.save(book);

        return mapper.mapToBookDto(book);
    }

    private BookDto removeBookCopy(BookDto bookDto, long quantity) throws ArithmeticException {
        Book book = getBookByBookId(bookDto.getBookId()).get();

        long booksTotal = book.getBookStock()
                .stream()
                .map(BookStock::getRemainedInStock)
                .mapToLong(v -> v - quantity)
                .sum();

        if (booksTotal < 0 ) {
            throw new ArithmeticException(ErrorMessages.STOCK_IS_EMPTY.getErrorMessage());
        }

        book.getBookStock().forEach(v -> v.setRemainedInStock(booksTotal));

        bookRepository.save(book);

        return mapper.mapToBookDto(book);
    }

    @SuppressWarnings("Duplicates")
    public BookDto markAsDestroyed(BookDto bookDto, long quantity) throws ArithmeticException {
        Book book = getBookByBookId(bookDto.getBookId()).get();

        removeBookCopy(mapper.mapToBookDto(book), quantity);

        long remainedBooks = book.getBookStock()
                .stream()
                .map(BookStock::getRemainedToRent)
                .mapToLong(v -> v - quantity)
                .sum();

        long booksDestroyed = book.getBookStock()
                .stream()
                .map(BookStock::getDestroyed)
                .reduce(quantity, Long::sum);

        if (booksDestroyed < 0) {
            throw new ArithmeticException(ErrorMessages.STOCK_IS_EMPTY.getErrorMessage());
        }

        book.getBookStock().forEach(v -> v.setRemainedToRent(remainedBooks));
        book.getBookStock().forEach(v -> v.setDestroyed(booksDestroyed));

        bookRepository.save(book);

        return mapper.mapToBookDto(book);
    }

    @SuppressWarnings("Duplicates")
    public BookDto markAsLost(String bookId, long quantity) throws ArithmeticException {
        Book book = getBookByBookId(bookId).get();

        removeBookCopy(mapper.mapToBookDto(book), quantity);

        long remainedBooks = book.getBookStock()
                .stream()
                .map(BookStock::getRemainedToRent)
                .mapToLong(v -> v - quantity)
                .sum();

        long booksLost = book.getBookStock()
                .stream()
                .map(BookStock::getLost)
                .reduce(quantity, Long::sum);

        if (booksLost < 0) {
            throw new ArithmeticException(ErrorMessages.STOCK_IS_EMPTY.getErrorMessage());
        }

        book.getBookStock().forEach(v -> v.setRemainedToRent(remainedBooks));
        book.getBookStock().forEach(v -> v.setLost(booksLost));

        bookRepository.save(book);

        return mapper.mapToBookDto(book);
    }

    @SuppressWarnings("Duplicates")
    public Book markAsRented(BookDto bookDto, long quantity) throws ArithmeticException {
        Book book = getBookByBookId(bookDto.getBookId()).get();

        long remainedBooks = book.getBookStock()
                .stream()
                .map(BookStock::getRemainedToRent)
                .mapToLong(v -> v - quantity)
                .sum();

        long booksRented = book.getBookStock()
                .stream()
                .map(BookStock::getRented)
                .reduce(quantity, Long::sum);

        if (remainedBooks < 0 || booksRented < 0) {
            throw new ArithmeticException(ErrorMessages.STOCK_IS_EMPTY.getErrorMessage());
        }
        book.getBookStock().forEach(v -> v.setRemainedToRent(remainedBooks));
        book.getBookStock().forEach(v -> v.setRented(booksRented));

        bookRepository.save(book);

        return book;
    }

    @SuppressWarnings("Duplicates")
    public Book markAsReturn(String bookId, long quantity) throws ArithmeticException  {
        Book book = getBookByBookId(bookId).get();

        long remainedBooks = book.getBookStock()
                .stream()
                .map(BookStock::getRemainedToRent)
                .reduce(quantity, Long::sum);

        long booksRented = book.getBookStock()
                .stream()
                .map(BookStock::getRented)
                .mapToLong(v -> v - quantity)
                .sum();

        book.getBookStock().forEach(v -> v.setRemainedToRent(remainedBooks));
        book.getBookStock().forEach(v -> v.setRented(booksRented));

        bookRepository.save(book);

        return book;
    }

}
