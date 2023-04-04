package ru.practicum.common.exceptions;

public class NotBelongEventToUserInitiator extends RuntimeException {
    public NotBelongEventToUserInitiator(final long message1, final long message2) {
        super("Событие с id= " + message1 + " не принадлежит пользовтелю с id= " + message2);
    }
}
