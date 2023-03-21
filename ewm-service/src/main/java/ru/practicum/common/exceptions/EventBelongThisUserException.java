package ru.practicum.common.exceptions;

public class EventBelongThisUserException extends RuntimeException {
    public EventBelongThisUserException(final long message1, final long message2) {
        super("Событие с id= " + message1 + " было инициировано данным пользователем с id= " + message2);
    }
}
