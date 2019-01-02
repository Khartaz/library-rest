package com.library.domain.dto;

import java.util.Date;

public class RentedBookDto {
    private String bookId;
    private String readerId;
    private Date rentDate;
    private Date returnDate;
    private long numberOfBooks;
    private boolean returned = false;
    private Date dateOfReturnBook = null;

    public RentedBookDto() {
    }

    public RentedBookDto(String bookId, String readerId, Date rentDate, Date returnDate,
                         long numberOfBooks, boolean returned, Date dateOfReturnBook) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.numberOfBooks = numberOfBooks;
        this.returned = returned;
        this.dateOfReturnBook = dateOfReturnBook;
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
}
