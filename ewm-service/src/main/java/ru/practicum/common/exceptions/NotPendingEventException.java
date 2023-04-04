package ru.practicum.common.exceptions;

public class NotPendingEventException extends RuntimeException {
    public NotPendingEventException(final String message) {
        super(message);
    }
}
