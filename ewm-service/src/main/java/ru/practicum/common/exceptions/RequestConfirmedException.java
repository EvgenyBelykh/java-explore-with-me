package ru.practicum.common.exceptions;

public class RequestConfirmedException extends RuntimeException {
    public RequestConfirmedException() {
        super("Нельзя отменить уже подтвежденную заявку");
    }
}