package com.library.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "readers")
public class Reader {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "reader_id")
    private String readerId;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "created")
    private Date created;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RentedBook> rentedBooks = new ArrayList<>();

    public Reader() {
    }

    public Reader(String readerId, String firstname, String lastname, Date created) {
        this.readerId = readerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.created = created;
    }

    public Reader(String firstname, String lastname, Date created) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.created = created;
    }

    public Reader(String readerId, String firstname, String lastname, Date created, List<RentedBook> rentedBooks) {
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

    public List<RentedBook> getRentedBooks() {
        return rentedBooks;
    }

    public void setRentedBooks(List<RentedBook> rentedBooks) {
        this.rentedBooks = rentedBooks;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", readerId='" + readerId + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", created=" + created +
                '}';
    }

}
