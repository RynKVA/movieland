package com.rynkovoi.exception;

public class MovieRequestNotValidException extends RuntimeException{
    public MovieRequestNotValidException(String message) {
        super(message);
    }
}
