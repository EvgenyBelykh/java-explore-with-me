package ru.practicum.common.exceptions;

public class PendingOrCanceledEventException extends RuntimeException {
    public PendingOrCanceledEventException() {
        super("Only pending or canceled events can be changed");
    }
}
