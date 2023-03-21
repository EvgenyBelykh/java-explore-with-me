package ru.practicum.common.exceptions;

public class ExistParticipationRequestFromUserException extends RuntimeException {
    public ExistParticipationRequestFromUserException(final long message1, final long message2) {
        super("Пользователь с id= " + message2 + " уже зарегестрирован на событие с id= " + message1);
    }
}