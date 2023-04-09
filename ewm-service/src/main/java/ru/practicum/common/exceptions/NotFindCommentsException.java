package ru.practicum.common.exceptions;

public class NotFindCommentsException extends RuntimeException {
    public NotFindCommentsException(final long message) {
        super("Comments with id= " + message + " was not found");
    }
}