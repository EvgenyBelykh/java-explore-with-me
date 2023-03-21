package ru.practicum.apiPrivate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiPrivate.services.EventServicePrivate;
import ru.practicum.common.dto.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
@Slf4j
public class EventControllerPrivate {
    private final EventServicePrivate eventServicePrivate;

    @PostMapping
    public EventFullDto add(@PathVariable("userId") Long idUser,
                            @Valid @RequestBody NewEventDto newEventDto) {

        log.info("Запрос добавления нового события {}", newEventDto.getTitle());
        return eventServicePrivate.add(idUser, newEventDto);
    }

    @GetMapping
    public List<EventFullDto> getAllAddedByIdUser(@PathVariable("userId") Long idUser,
                                                  @RequestParam(value = "from", defaultValue = "0") Integer from,
                                                  @RequestParam(value = "size", defaultValue = "10") Integer size) {

        log.info("Запрос получения событий, добавленных пользователем с id={}", idUser);
        return eventServicePrivate.getAllAddedByIdUser(idUser, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getAddedByIdUser(@PathVariable("userId") Long idUser,
                                         @PathVariable("eventId") Long idEvent) {

        log.info("Запрос получения полной информации о событии c id= {} добавленном пользователем с id={}",
                idEvent, idUser);
        return eventServicePrivate.getAddedByIdUser(idUser, idEvent);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchAddedByIdUser(@PathVariable("userId") Long idUser,
                                           @PathVariable("eventId") Long idEvent,
                                           @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {

        log.info("Запрос получения полной информации о событии c id= {} добавленном пользователем с id={}",
                idEvent, idUser);
        return eventServicePrivate.patchAddedByIdUser(idUser, idEvent, updateEventUserRequest);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getAllParticipationRequestsByIdInitiator(@PathVariable("userId") Long initiatorId,
                                                                                  @PathVariable("eventId") Long idEvent) {

        log.info("Запрос получения информации о запросах на участие в событии c id= {} пользователя с id={} " +
                "опубликовашего событие", idEvent, initiatorId);
        return eventServicePrivate.getAllParticipationRequestsByIdInitiator(initiatorId, idEvent);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult patchParticipationRequest(@PathVariable("userId") Long initiatorId,
                                                                   @PathVariable("eventId") Long idEvent,
                                 @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {

        log.info("Запрос изменения статуса заявок на участия в событии с id= {} пользователя с id= {} " +
                "опубликовавшего событие", idEvent, initiatorId);
        return eventServicePrivate.patchParticipationRequest(initiatorId, idEvent, eventRequestStatusUpdateRequest);
    }
}