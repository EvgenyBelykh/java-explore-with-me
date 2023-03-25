package ru.practicum.common.exceptions;

public class NameCategoryIsBlankException extends RuntimeException {
    public NameCategoryIsBlankException(final String message) {
        super(message);
    }
}
