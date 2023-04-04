package ru.practicum.common.exceptions;

public class NotPendingParticipationRequestException extends RuntimeException {
    public NotPendingParticipationRequestException(final String message) {
        super(message);
    }
}
