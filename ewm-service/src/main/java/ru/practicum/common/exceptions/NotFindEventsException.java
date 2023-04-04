package ru.practicum.common.exceptions;

public class NotFindEventsException extends RuntimeException {
    public NotFindEventsException() {
        super("Field: events. Error: must not be blank. Value: null");
    }
}

