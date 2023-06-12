package com.bikkadIT.ElectronicStore.exceptions;

public class BadApiException extends RuntimeException {

    public BadApiException() {
        super("Bad Request !!!");
    }

    public BadApiException(String message) {
        super(message);
    }
}