package ru.practicum.common.exceptions;

public class NotPublishedPublicEventException extends RuntimeException {
    public NotPublishedPublicEventException(final Long message) {
        super("Event with id= " + message + " was not found");
    }
}

