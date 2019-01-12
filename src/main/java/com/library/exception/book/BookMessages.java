package com.library.exception.book;

public enum BookMessages {

    BOOK_RENTED("Book with provided id already rented."),
    BOOKS_TO_LESS("Books rented with provided id is less. Please enter correct quantity."),
    RENT_FIRST("Book with provided id is not rented yet."),
    BOOK_EXISTS("Book with provided id already exist."),
    STOCK_IS_EMPTY("Not enough books in stock with provided id."),
    BOOK_NOT_FOUND("Book with provided id is not found.");

    private String errorMessage;

    BookMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
