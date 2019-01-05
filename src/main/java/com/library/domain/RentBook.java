package com.library.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rent_books")
public class RentBook {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "book_id")
    private String bookId;

    @Column(name = "reader_id")
    private String readerId;

    @Column(name = "rent_date")
    private Date rentDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "number_of_rented_books")
    private long numberOfBooks;

    @Column(name = "is_returned")
    private boolean returned = false;

    @Column(name = "date_of_return")
    private Date dateOfReturnBook = null;

    public RentBook() {
    }

    public RentBook(String bookId, String readerId, Date rentDate, Date returnDate,
                    long numberOfBooks, boolean returned, Date dateOfReturnBook) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.numberOfBooks = numberOfBooks;
        this.returned = returned;
        this.dateOfReturnBook = dateOfReturnBook;
    }

    public RentBook(Date rentDate, Date returnDate) {
        this.rentDate = rentDate;
        this.returnDate = returnDate;
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

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public long getNumberOfBooks() {
        return numberOfBooks;
    }

    public void setNumberOfBooks(long numberOfBooks) {
        this.numberOfBooks = numberOfBooks;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public Date getDateOfReturnBook() {
        return dateOfReturnBook;
    }

    public void setDateOfReturnBook(Date dateOfReturnBook) {
        this.dateOfReturnBook = dateOfReturnBook;
    }

    @Override
    public String toString() {
        return "RentBook{" +
                "id=" + id +
                ", bookId='" + bookId + '\'' +
                ", readerId='" + readerId + '\'' +
                ", rentDate=" + rentDate +
                ", returnDate=" + returnDate +
                '}';
    }

}
