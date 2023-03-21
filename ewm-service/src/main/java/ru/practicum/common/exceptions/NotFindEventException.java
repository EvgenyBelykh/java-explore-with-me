package ru.practicum.common.exceptions;

public class NotFindEventException extends RuntimeException {
    public NotFindEventException(final long message) {
        super("Event with id=" + message + " was not found");
    }
}
