package com.library.exception;

public enum BookMessages {

    BOOK_RENTED("Book with provided id already rented"),
    BOOKS_TO_LESS("Books rented with provided id is less. Please enter correct quantity"),
    RENT_FIRST("Please first rent some books"),
    BOOK_EXISTS("Book with provided id already exist");

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
