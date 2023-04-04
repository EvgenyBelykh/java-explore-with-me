package ru.practicum.apiPrivate.services;

import ru.practicum.common.dto.*;

import java.util.List;

public interface EventServicePrivate {
    EventFullDto add(Long idUser, NewEventDto newEventDto);

    List<EventFullDto> getAllAddedByIdUser(Long idUser, Integer from, Integer size);

    EventFullDto getAddedByIdUser(Long idUser, Long idEvent);

    EventFullDto patchAddedByIdUser(Long idUser, Long idEvent, UpdateEventUserRequest updateEventUserRequest);

    List<ParticipationRequestDto> getAllParticipationRequestsByIdInitiator(Long initiatorId, Long idEvent);

    EventRequestStatusUpdateResult patchParticipationRequest(Long initiatorId, Long idEvent,
                                                  EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}
