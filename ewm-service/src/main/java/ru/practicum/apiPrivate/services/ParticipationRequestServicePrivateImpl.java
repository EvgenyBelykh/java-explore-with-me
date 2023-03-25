package ru.practicum.apiPrivate.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.dto.ParticipationRequestDto;
import ru.practicum.common.enums.State;
import ru.practicum.common.enums.Status;
import ru.practicum.common.exceptions.*;
import ru.practicum.common.mappers.ParticipationRequestMapper;
import ru.practicum.common.models.Event;
import ru.practicum.common.models.ParticipationRequest;
import ru.practicum.common.models.User;
import ru.practicum.common.repositories.EventRepository;
import ru.practicum.common.repositories.ParticipationRequestRepository;
import ru.practicum.common.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ParticipationRequestServicePrivateImpl implements ParticipationRequestServicePrivate {
    private final ParticipationRequestRepository participationRequestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    private final ParticipationRequestMapper participationRequestMapper;

    @Override
    public List<ParticipationRequestDto> get(Long idUser) {
        userRepository.findById(idUser).orElseThrow(() -> new NotFindUserException(idUser));
        List<ParticipationRequest> participationRequests = participationRequestRepository.findAllByRequesterId(idUser);

        log.info("ApiPrivate. Возвращена информация о заявках пользователя с id={} на участие в чужих событиях",
                idUser);

        if (participationRequests.isEmpty()) {
            return Collections.emptyList();
        }

        return participationRequests.stream()
                .map(participationRequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto add(Long idUser, Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFindEventException(eventId));
        User requester = userRepository.findById(idUser).orElseThrow(() -> new NotFindUserException(idUser));

        if (participationRequestRepository.findByRequesterIdAndEventId(eventId, idUser).isPresent()) {
            throw new ExistParticipationRequestFromUserException(eventId, idUser);
        }

        if (event.getInitiator().getId() == idUser) {
            throw new EventBelongThisUserException(eventId, idUser);
        }

        if (!(event.getState() == State.PUBLISHED)) {
            throw new NotPublishedPrivateEventException("Event must be published");
        }

        if (event.getParticipantLimit() != 0 && event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new FullConfirmedRequestException(eventId);
        }

        ParticipationRequest participationRequest = new ParticipationRequest();
        participationRequest.setEvent(event);
        participationRequest.setRequester(requester);
        participationRequest.setCreated(LocalDateTime.now());

        if (!event.getRequestModeration()) {
            participationRequest.setStatus(Status.CONFIRMED);

            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
            log.info("Обновлено количество участников в событии с id= {}", eventId);

        } else {
            participationRequest.setStatus(Status.PENDING);
        }

        participationRequest = participationRequestRepository.save(participationRequest);
        log.info("ApiPrivate. vСохранен и отправлен запрос на участие на событие с id= {} от пользователя с id= {}",
                eventId, idUser);

        return participationRequestMapper.toParticipationRequestDto(participationRequest);
    }

    @Override
    public ParticipationRequestDto patch(Long idUser, Long requestId) {
        ParticipationRequest participationRequest = participationRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFindParticipationRequest(requestId));
        Long eventId = participationRequest.getEvent().getId();

        Event event = eventRepository.findById(participationRequest.getEvent().getId())
                .orElseThrow(() -> new NotFindEventException(eventId));

        participationRequest.setStatus(Status.CANCELED);
        participationRequest = participationRequestRepository.save(participationRequest);
        log.info("ApiPrivate. Пользователь с id= {} отменил свою заявку с id= {} на участие в событии с id= {}",
                idUser, requestId, eventId);

        return participationRequestMapper.toParticipationRequestDto(participationRequest);
    }
}
