package ru.practicum.common.exceptions;

public class NotFindParticipationRequest extends RuntimeException {
    public NotFindParticipationRequest(final long message) {
        super("Request with id= " + message + " was not found");
    }
}