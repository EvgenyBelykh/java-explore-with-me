package ru.practicum.common.exceptions;

public class EventTimeException extends RuntimeException {
    public EventTimeException(final String message) {
        super(message);
    }
}
