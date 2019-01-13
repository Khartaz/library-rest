package com.library.exception.reader;

public enum  ReaderMessages {

    READER_NOT_FOUND("Reader with provided id is not found"),
    READER_EXIST("Reader with provided readerId already exist");

    private String errorMessage;

    ReaderMessages(String errorMessage) {
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
