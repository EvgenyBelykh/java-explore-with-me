package ru.practicum.common.exceptions;

public class NotFindUsersException extends RuntimeException {
    public NotFindUsersException(final String message) {
        super(message);
    }
}
