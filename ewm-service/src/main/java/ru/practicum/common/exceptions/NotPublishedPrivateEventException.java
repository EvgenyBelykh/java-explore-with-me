package ru.practicum.common.exceptions;

public class NotPublishedPrivateEventException extends RuntimeException {
    public NotPublishedPrivateEventException(final String message) {
        super(message);
    }
}
