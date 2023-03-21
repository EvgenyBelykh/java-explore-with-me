package ru.practicum.apiPrivate.services;

import ru.practicum.common.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationRequestServicePrivate {
    List<ParticipationRequestDto> get(Long idUser);

    ParticipationRequestDto add(Long idUser, Long eventId);

    ParticipationRequestDto patch(Long idUser, Long requestId);
}
