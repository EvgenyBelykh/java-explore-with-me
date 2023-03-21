package ru.practicum.common.exceptions;

public class ExistNameCategoryException extends RuntimeException {
    public ExistNameCategoryException(final String message) {
        super("Категория name= " + message + " уже есть в базе");
    }
}
