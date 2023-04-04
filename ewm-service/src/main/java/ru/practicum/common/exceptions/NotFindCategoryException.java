package ru.practicum.common.exceptions;

public class NotFindCategoryException extends RuntimeException {
    public NotFindCategoryException(final long message) {
        super("Category with id= " + message + " was not found");
    }
}
