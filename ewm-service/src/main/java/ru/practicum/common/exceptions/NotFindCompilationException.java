package ru.practicum.common.exceptions;

public class NotFindCompilationException extends RuntimeException {
    public NotFindCompilationException(final long message) {
        super("Compilation with id= " + message + " was not found");
    }
}
