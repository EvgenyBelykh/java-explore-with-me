package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.common.error.ApiError;
import ru.practicum.common.exceptions.*;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({NameCategoryIsBlankException.class,
            NotBelongEventToUserInitiator.class,
            NotPendingParticipationRequestException.class,
            NotConfirmedOrRejectedEventStatusUpdateException.class,
            MethodArgumentNotValidException.class,
            NotFindEventsException.class,
            MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(Exception e) {
        log.info("400 Bad_Request");

        return new ApiError(
                "Bad Request",
                "Incorrectly made request.",
                e.getMessage()
        );
    }

    @ExceptionHandler({NotFindUserException.class,
            NotFindUsersException.class,
            NotFindCategoryException.class,
            NotFindEventException.class,
            NotFindCompilationException.class,
            NotFindParticipationRequest.class,
            NotPublishedPublicEventException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(Exception e) {
        log.info("404 Not_Found");

        return new ApiError(
                "NOT_FOUND",
                "The required object was not found.",
                e.getMessage());
    }

    @ExceptionHandler({ExistEmailUserException.class,
            ExistNameCategoryException.class,
            TooLateEventException.class,
            PendingOrCanceledEventException.class,
            PublishedEventException.class,
            NotPendingEventException.class,
            ExistParticipationRequestFromUserException.class,
            EventBelongThisUserException.class,
            NotPublishedPrivateEventException.class,
            FullConfirmedRequestException.class,
            EventUseThisCategoryException.class,
            RequestConfirmedException.class
            })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(Exception e) {
        log.info("409 Conflict");

        return new ApiError(
                "CONFLICT",
                "Integrity constraint has been violated.",
                e.getMessage());
    }
}
