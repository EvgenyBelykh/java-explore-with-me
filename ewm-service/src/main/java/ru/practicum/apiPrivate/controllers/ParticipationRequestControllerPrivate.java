package ru.practicum.apiPrivate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiPrivate.services.ParticipationRequestServicePrivate;
import ru.practicum.common.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
@Slf4j
public class ParticipationRequestControllerPrivate {
    private final ParticipationRequestServicePrivate participationRequestServicePrivate;

    @GetMapping
    public List<ParticipationRequestDto> get(@PathVariable("userId") Long idUser) {

        log.info("Запрос получения информации о заявках пользователя с id={} на участие в чужих событиях",
                idUser);
        return participationRequestServicePrivate.get(idUser);
    }

    @PostMapping
    public ParticipationRequestDto add(@PathVariable("userId") Long idUser,
                                       @RequestParam(value = "eventId") Long eventId) {

        log.info("Запрос от пользователя с id= {} на участие в событии с id= {}", idUser, eventId);
        return participationRequestServicePrivate.add(idUser, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto patch(@PathVariable("userId") Long idUser,
                                         @PathVariable("requestId") Long requestId) {

        log.info("Запрос отмены заявки с id= {} на участие в событии от пользователя с id= {}", requestId, idUser);
        return participationRequestServicePrivate.patch(idUser, requestId);
    }
}
