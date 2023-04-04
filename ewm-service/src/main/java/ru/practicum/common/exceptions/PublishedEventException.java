package ru.practicum.common.exceptions;

public class PublishedEventException extends RuntimeException {
    public PublishedEventException() {
        super("Cannot publish the event because it's not in the right state: PUBLISHED");
    }
}