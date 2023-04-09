package ru.practicum.common.exceptions;

public class NotBelongCommentToUserException extends RuntimeException {
    public NotBelongCommentToUserException(final long message1, final long message2) {
        super("Комментарий с id= " + message1 + " не принадлежит пользовтелю с id= " + message2);
    }
}