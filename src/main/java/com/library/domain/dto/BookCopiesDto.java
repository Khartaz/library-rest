package com.library.domain.dto;


public class BookCopiesDto {
    private long id;
    private String bookId;
    private long total;
    private long remainedInStock;
    private long remainedToRent;
    private long rented;
    private long destroyed;
    private long lost;

    public BookCopiesDto() {
    }

    public BookCopiesDto(String bookId, long total, long remainedInStock,
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
}
