package ru.practicum.common.exceptions;

public class NotFindUserException extends RuntimeException {
    public NotFindUserException(final long message) {
        super("User with " + message + " was not found");
    }
}
