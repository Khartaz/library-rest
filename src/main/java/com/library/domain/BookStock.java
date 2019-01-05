package com.library.domain;

import javax.persistence.*;

@Entity
@Table(name = "book_stock")
public class BookStock {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "book_id")
    private String bookId;

    @Column(name = "total_purchased_books")
    private long total;

    @Column(name = "remained_in_stock")
    private long remainedInStock;

    @Column(name = "remained_to_rent")
    private long remainedToRent;

    @Column(name = "rented")
    private long rented;

    @Column(name = "destroyed")
    private long destroyed;

    @Column(name = "lost")
    private long lost;

    public BookStock() {
    }

    public BookStock(long total, long remainedToRent, long rented, long destroyed, long lost) {
        this.total = total;
        this.remainedToRent = remainedToRent;
        this.rented = rented;
        this.destroyed = destroyed;
        this.lost = lost;
    }

    public BookStock(String bookId, long total, long remainedInStock,
                     long remainedToRent, long rented, long destroyed, long lost) {
        this.bookId = bookId;
        this.total = total;
        this.remainedInStock = remainedInStock;
        this.remainedToRent = remainedToRent;
        this.rented = rented;
        this.destroyed = destroyed;
        this.lost = lost;
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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getRemainedInStock() {
        return remainedInStock;
    }

    public void setRemainedInStock(long remainedInStock) {
        this.remainedInStock = remainedInStock;
    }

    public long getRemainedToRent() {
        return remainedToRent;
    }

    public void setRemainedToRent(long remainedToRent) {
        this.remainedToRent = remainedToRent;
    }

    public long getRented() {
        return rented;
    }

    public void setRented(long rented) {
        this.rented = rented;
    }

    public long getDestroyed() {
        return destroyed;
    }

    public void setDestroyed(long destroyed) {
        this.destroyed = destroyed;
    }

    public long getLost() {
        return lost;
    }

    public void setLost(long lost) {
        this.lost = lost;
    }

    @Override
    public String toString() {
        return "BookStock{" +
                "id=" + id +
                ", bookId='" + bookId + '\'' +
                ", rented=" + rented +
                ", destroyed=" + destroyed +
                ", lost=" + lost +
                '}';
    }

}
