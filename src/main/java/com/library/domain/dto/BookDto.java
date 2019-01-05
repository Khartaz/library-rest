package com.library.domain.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookDto {
    private String bookId;
    private String title;
    private String author;
    private Date yearOfPublication;
    private List<BookStockDto> bookCopies = new ArrayList<>();

    public BookDto() {
    }

    public BookDto(String bookId, String title, String author,
                   Date yearOfPublication) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
    }

    public BookDto(String bookId, String title, String author, Date yearOfPublication, List<BookStockDto> bookCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.bookCopies = bookCopies;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Date yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public List<BookStockDto> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(List<BookStockDto> bookCopies) {
        this.bookCopies = bookCopies;
    }
}
