package ru.practicum.common.exceptions;

public class TooLateEventException extends RuntimeException {
    public TooLateEventException(final String message) {
        super("Намеченное событие " + message + "начинается слишком позно");
    }
}
