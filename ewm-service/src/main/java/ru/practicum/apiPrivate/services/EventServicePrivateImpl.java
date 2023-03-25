package ru.practicum.apiPrivate.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.dto.*;
import ru.practicum.common.enums.State;
import ru.practicum.common.enums.StateAction;
import ru.practicum.common.enums.Status;
import ru.practicum.common.exceptions.*;
import ru.practicum.common.mappers.*;
import ru.practicum.common.models.*;
import ru.practicum.common.repositories.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EventServicePrivateImpl implements EventServicePrivate {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    private final ParticipationRequestRepository participationRequestRepository;

    private final LocationMapper locationMapper;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;

    private final ParticipationRequestMapper participationRequestMapper;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public EventFullDto add(Long idUser, NewEventDto newEventDto) {
        User initiator = userRepository.findById(idUser).orElseThrow(() -> new NotFindUserException(idUser));

        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() ->
                new NotFindCategoryException(newEventDto.getCategory()));

        if (LocalDateTime.parse(newEventDto.getEventDate(), dateTimeFormatter).minusHours(2)
                .isBefore(LocalDateTime.now())) {
            throw new TooLateEventException(newEventDto.getTitle());
        }

        LocationModel locationModel;
        if (newEventDto.getLocation().getLat() == null || newEventDto.getLocation().getLon() == null) {
            locationModel = null;
        } else {
            locationModel = locationRepository.save(locationMapper.toLocationModel(null, newEventDto.getLocation()));
        }

        if (newEventDto.getPaid() == null) {
            newEventDto.setPaid(false);
        }

        if (newEventDto.getParticipantLimit() == null) {
            newEventDto.setParticipantLimit(0);
        }

        if (newEventDto.getRequestModeration() == null) {
            newEventDto.setRequestModeration(true);
        }

        Event event = Event.builder()
                .title(newEventDto.getTitle())
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .category(category)
                .locationModel(locationModel)
                .initiator(initiator)
                .eventDate(LocalDateTime.parse(newEventDto.getEventDate(), dateTimeFormatter))
                .createdOn(LocalDateTime.now())
                .publishedOn(null)
                .confirmedRequests(0L)
                .requestModeration(newEventDto.getRequestModeration())
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .state(State.PENDING)
                .views(0L)
                .build();

        event = eventRepository.save(event);
        log.info("ApiPrivate. Сохранено событие: {} пользователем с id={}", event.getTitle(), idUser);

        return eventMapper.toEventFullDto(event);
    }

    @Override
    public List<EventFullDto> getAllAddedByIdUser(Long idUser, Integer from, Integer size) {
        User initiator = userRepository.findById(idUser).orElseThrow(() -> new NotFindUserException(idUser));
        userMapper.toUserShortDto(initiator);

        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Event> eventsPage = eventRepository.findAllByInitiatorId(idUser, pageable);

        List<Event> events = eventsPage.getContent();
        List<EventFullDto> eventsFullDto = new ArrayList<>();

        for (Event event : events) {
            eventsFullDto.add(eventMapper.toEventFullDto(
                    event
            ));
        }

        log.info("ApiPrivate. Возвращен список событий, добавленных пользователем с id={}", idUser);
        return eventsFullDto;
    }

    @Override
    public EventFullDto getAddedByIdUser(Long idUser, Long idEvent) {
        userRepository.findById(idUser).orElseThrow(() -> new NotFindUserException(idUser));
        Event event = eventRepository.findById(idEvent).orElseThrow(() -> new NotFindEventException(idEvent));

        checkBelongEventToUserInitiator(event, idEvent, idUser);

        log.info("ApiPrivate. Возвращена полная информация о событии с id= {} добавленном пользователем с id={}", idEvent, idUser);
        return eventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto patchAddedByIdUser(Long idUser, Long idEvent, UpdateEventUserRequest updateEventUserRequest) {
        userRepository.findById(idUser).orElseThrow(() -> new NotFindUserException(idUser));
        Event event = eventRepository.findById(idEvent).orElseThrow(() -> new NotFindEventException(idEvent));

        checkBelongEventToUserInitiator(event, idEvent, idUser);
        checkState(updateEventUserRequest, event);
        checkOrUpdateEventDate(updateEventUserRequest, event);
        checkOrUpdateTitle(updateEventUserRequest, event);
        checkOrUpdateAnnotation(updateEventUserRequest, event);
        checkOrUpdateDescription(updateEventUserRequest, event);
        checkOrUpdateCategory(updateEventUserRequest, event);
        checkOrUpdateLocation(updateEventUserRequest, event);
        checkOrUpdatePaid(updateEventUserRequest, event);
        checkOrUpdateParticipantLimit(updateEventUserRequest, event);
        checkOrUpdateRequestModeration(updateEventUserRequest, event);

        eventRepository.save(event);
        log.info("Обновлено событие с id= {}", event.getId());

        return eventMapper.toEventFullDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getAllParticipationRequestsByIdInitiator(Long initiatorId, Long idEvent) {
        userRepository.findById(initiatorId).orElseThrow(() -> new NotFindUserException(initiatorId));
        Event event = eventRepository.findById(idEvent).orElseThrow(() -> new NotFindEventException(idEvent));
        checkBelongEventToUserInitiator(event, idEvent, initiatorId);

        List<ParticipationRequest> participationRequests = participationRequestRepository.findAllByEventId(idEvent);
        if (participationRequests == null) {
            log.info("ApiPrivate. Возвращен пустой список заявок на участие в событии с id= {}", idEvent);
            return Collections.emptyList();
        }

        log.info("ApiPrivate. Возвращен список с информацией о запросах на участие в событии c id= {} пользователя с id={} " +
                "опубликовашего событие", idEvent, initiatorId);

        return participationRequests.stream().map(participationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResult patchParticipationRequest(Long initiatorId, Long idEvent,
                                              EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        userRepository.findById(initiatorId).orElseThrow(() -> new NotFindUserException(initiatorId));
        Event event = eventRepository.findById(idEvent).orElseThrow(() -> new NotFindEventException(idEvent));

        Iterable<ParticipationRequest> participationRequestIterables = participationRequestRepository
                .findAllById(eventRequestStatusUpdateRequest.getRequestIds());

        List<ParticipationRequest> participationRequests = new ArrayList<>();

        for (ParticipationRequest participationRequestIterable : participationRequestIterables) {
            participationRequests.add(participationRequestIterable);
        }

        if (event.getParticipantLimit() > 0 && (Objects.equals(event.getConfirmedRequests(), Long.valueOf(event.getParticipantLimit())))) {
            throw new FullConfirmedRequestException(idEvent);
        }

        //если необходимо подтвердить заявки
        if (Objects.equals(eventRequestStatusUpdateRequest.getStatus(), Status.CONFIRMED.toString())) {
                if (event.getParticipantLimit() == 0) {
                    for (ParticipationRequest participationRequest : participationRequests) {
                        participationRequest.setStatus(Status.CONFIRMED);
                        participationRequestRepository.save(participationRequest);
                    }
                    log.info("Заявкам с id= {} подтверждено участие в событии с id= {}",
                            participationRequests, event.getId());
                    event.setConfirmedRequests(event.getConfirmedRequests() + participationRequests.size());
                    eventRepository.save(event);

                    log.info("Обновлено количество участников (+ {}) в событии с id= {} ",
                            participationRequests.size(), event.getId());
                } else {
                    int participantLimit = event.getParticipantLimit();
                    int confirmedRequests = Math.toIntExact(event.getConfirmedRequests());
                    for (ParticipationRequest participationRequest : participationRequests) {
                        if (participantLimit >= confirmedRequests) {
                            participationRequest.setStatus(Status.CONFIRMED);
                            participationRequestRepository.save(participationRequest);

                            log.info("Заявке с id= {} подтвеждено участие в событии с id= {}",
                                    participationRequest.getId(), event.getId());
                        } else {
                            participationRequest.setStatus(Status.REJECTED);
                            participationRequestRepository.save(participationRequest);

                            log.info("Заявке с id= {} отклонено участие в событии с id= {} превышен лимит участников",
                                    participationRequest.getId(), event.getId());
                        }
                        confirmedRequests++;
                    }
                    if (participantLimit >= confirmedRequests) {
                        event.setConfirmedRequests(event.getConfirmedRequests() + confirmedRequests);
                        eventRepository.save(event);

                        log.info("Обновлено количество участников (+ {}) в событии с id= {} ",
                                confirmedRequests, event.getId());
                    } else {
                        event.setConfirmedRequests((long) participantLimit);
                        log.info("На событие с id= {} записалось максимальное количество ({}) пользователей",
                                event.getId(), participantLimit);
                    }
                }

            //если необходимо отклонить заявки
        } else if (Objects.equals(eventRequestStatusUpdateRequest.getStatus(), Status.REJECTED.toString())) {
            for (ParticipationRequest participationRequest : participationRequests) {
                if (participationRequest.getStatus() == Status.CONFIRMED) {
                    throw new RequestConfirmedException();
                }
                participationRequest.setStatus(Status.REJECTED);
                participationRequestRepository.save(participationRequest);
            }
            log.info("ApiPrivate. Заявкам с id= {} отклонено участие в событии с id= {}",
                    participationRequests, event.getId());
        } else {
            throw new NotConfirmedOrRejectedEventStatusUpdateException("ParticipationUpdateRequest must have status" +
                    " CONFIRMED or REJECTED");
        }

        return toEventRequestStatusUpdateResult(participationRequests, event.getId());
    }

    private EventRequestStatusUpdateResult toEventRequestStatusUpdateResult(List<ParticipationRequest> participationRequests,
                                                                            Long eventId){
        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();
        for (ParticipationRequest participationRequest : participationRequests) {
            if (participationRequest.getStatus() == Status.CONFIRMED) {
                confirmedRequests.add(participationRequestMapper.toParticipationRequestDto(participationRequest));
            }
            if (participationRequest.getStatus() == Status.REJECTED) {
                rejectedRequests.add(participationRequestMapper.toParticipationRequestDto(participationRequest));
            }
        }
        EventRequestStatusUpdateResult eventRequestStatusUpdateResult = new EventRequestStatusUpdateResult();
        eventRequestStatusUpdateResult.setConfirmedRequests(confirmedRequests);
        eventRequestStatusUpdateResult.setRejectedRequests(rejectedRequests);

        log.info("ApiPrivate. Возвращен результат изменения статуса заявок на участие в событии с id= {}", eventId);
        return eventRequestStatusUpdateResult;
    }

    private void checkBelongEventToUserInitiator(Event event, Long idEvent, Long idUser) {
        if (event.getInitiator().getId() != idUser) {
            throw new NotBelongEventToUserInitiator(idEvent, idUser);
        }
    }

    private void checkState(UpdateEventUserRequest updateEventUserRequest, Event event){
        if (event.getState() != State.CANCELED && (event.getState() != State.PENDING)) {
            throw new PendingOrCanceledEventException();
        } else {
            if (Objects.equals(updateEventUserRequest.getStateAction(), StateAction.CANCEL_REVIEW.toString())) {
                event.setState(State.CANCELED);
            } else if (Objects.equals(updateEventUserRequest.getStateAction(), StateAction.SEND_TO_REVIEW.toString())) {
                event.setState(State.PENDING);
            }
        }
    }

    private void checkOrUpdateEventDate(UpdateEventUserRequest updateEventUserRequest, Event event){
        if (event.getEventDate().minusHours(2).isBefore(LocalDateTime.now())) {
            throw new TooLateEventException(event.getTitle());
        } else {
            if (updateEventUserRequest.getEventDate() != null) {
                if (LocalDateTime.parse(updateEventUserRequest.getEventDate(), dateTimeFormatter).minusHours(2)
                        .isBefore(LocalDateTime.now())){
                    throw new TooLateEventException(updateEventUserRequest.getTitle());
                } else {
                    event.setEventDate(LocalDateTime.parse(updateEventUserRequest.getEventDate(), dateTimeFormatter));
                }
            }
        }
    }

    private void checkOrUpdateTitle(UpdateEventUserRequest updateEventUserRequest, Event event){
        if (updateEventUserRequest.getTitle() != null) {
            event.setTitle(updateEventUserRequest.getTitle());
        }
    }

    private void checkOrUpdateAnnotation(UpdateEventUserRequest updateEventUserRequest, Event event){
        if (updateEventUserRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventUserRequest.getAnnotation());
        }
    }

    private void checkOrUpdateDescription(UpdateEventUserRequest updateEventUserRequest, Event event){
        if (updateEventUserRequest.getDescription() != null) {
            event.setDescription(updateEventUserRequest.getDescription());
        }
    }

    private void checkOrUpdateCategory(UpdateEventUserRequest updateEventUserRequest, Event event){
        if (updateEventUserRequest.getCategory() != null) {
            Category category = categoryRepository.findById(updateEventUserRequest.getCategory()).orElseThrow(() ->
                    new NotFindCategoryException(updateEventUserRequest.getCategory()));
            event.setCategory(category);
        }
    }

    private void checkOrUpdateLocation(UpdateEventUserRequest updateEventUserRequest, Event event) {
        if (updateEventUserRequest.getLocation() != null) {
            Long oldLocationId = event.getLocationModel().getId();
            LocationModel locationModel = locationRepository
                    .save(locationMapper.toLocationModel(oldLocationId, updateEventUserRequest.getLocation()));
            event.setLocationModel(locationModel);
        }
    }

    private void checkOrUpdatePaid(UpdateEventUserRequest updateEventUserRequest, Event event){
        if (updateEventUserRequest.getPaid() != null) {
            event.setPaid(updateEventUserRequest.getPaid());
        }
    }

    private void checkOrUpdateParticipantLimit(UpdateEventUserRequest updateEventUserRequest, Event event) {
        if (updateEventUserRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
        }
    }

    private void checkOrUpdateRequestModeration(UpdateEventUserRequest updateEventUserRequest, Event event) {
        if (updateEventUserRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventUserRequest.getRequestModeration());
        }
    }

}
