package ru.practicum.common.exceptions;

public class ExistEmailUserException extends RuntimeException {
    public ExistEmailUserException(final String message) {
        super(message);
    }
}
