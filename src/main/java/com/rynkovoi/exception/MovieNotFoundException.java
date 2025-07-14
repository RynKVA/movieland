package com.rynkovoi.exception;

public class MovieNotFoundException extends RuntimeException{

    private static final String MESSAGE = "Movie not found with id: %d";

    public MovieNotFoundException(Long id) {
        super(MESSAGE.formatted(id));
    }
}
