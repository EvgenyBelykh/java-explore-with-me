package ru.practicum.common.exceptions;

public class NotConfirmedOrRejectedEventStatusUpdateException extends RuntimeException {
    public NotConfirmedOrRejectedEventStatusUpdateException(final String message) {
        super(message);
    }
}