package com.library.domain.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReaderDto {
    private long id;
    private String readerId;
    private String firstname;
    private String lastname;
    private Date created;
    private List<RentedBookDto> rentedBooks = new ArrayList<>();

    public ReaderDto() {
    }

    public ReaderDto(String readerId, String firstname, String lastname, Date created) {
        this.readerId = readerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.created = created;
    }

    public ReaderDto(String readerId, String firstname, String lastname, Date created, List<RentedBookDto> rentedBooks) {
        this.readerId = readerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.created = created;
        this.rentedBooks = rentedBooks;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<RentedBookDto> getRentedBooks() {
        return rentedBooks;
    }

    public void setRentedBooks(List<RentedBookDto> rentedBooks) {
        this.rentedBooks = rentedBooks;
    }
}
