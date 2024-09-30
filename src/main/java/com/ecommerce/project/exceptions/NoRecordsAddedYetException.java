package com.ecommerce.project.exceptions;

public class NoRecordsAddedYetException extends RuntimeException {

    public static final Long serialVersionUID = 1L;

    public NoRecordsAddedYetException() {

    }

    public NoRecordsAddedYetException(String message) {
        super(message);
    }
}
