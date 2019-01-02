package com.library.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "book_id")
    private String bookId;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "year_of_publication")
    private Date yearOfPublication;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BookCopies> bookCopies = new ArrayList<>();

    public Book() {
    }

    public Book(String title, String author, Date yearOfPublication) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
    }


    public Book(String bookId, String title, String author, Date yearOfPublication) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<BookCopies> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(List<BookCopies> bookCopies) {
        this.bookCopies = bookCopies;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                ", bookCopies=" + bookCopies +
                '}';
    }

}
