package ru.practicum.common.exceptions;

public class EventUseThisCategoryException extends RuntimeException {
    public EventUseThisCategoryException() {
        super("The category is not empty");
    }
}
