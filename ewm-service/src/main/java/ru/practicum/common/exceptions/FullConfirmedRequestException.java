package ru.practicum.common.exceptions;

public class FullConfirmedRequestException extends RuntimeException {
    public FullConfirmedRequestException(final long message) {
        super("На событие с id=" + message + " уже идет максимальное количество пользователей");
    }
}