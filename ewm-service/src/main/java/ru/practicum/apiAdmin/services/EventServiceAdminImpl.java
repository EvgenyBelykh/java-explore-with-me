package ru.practicum.apiAdmin.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.dto.EventFullDto;
import ru.practicum.common.dto.UpdateEventAdminRequest;
import ru.practicum.common.enums.State;
import ru.practicum.common.enums.StateAction;
import ru.practicum.common.exceptions.*;
import ru.practicum.common.mappers.EventMapper;
import ru.practicum.common.mappers.LocationMapper;
import ru.practicum.common.models.Category;
import ru.practicum.common.models.Event;
import ru.practicum.common.models.LocationModel;
import ru.practicum.common.models.User;
import ru.practicum.common.repositories.CategoryRepository;
import ru.practicum.common.repositories.EventRepository;
import ru.practicum.common.repositories.LocationRepository;
import ru.practicum.common.repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EventServiceAdminImpl implements EventServiceAdmin {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    private final EventMapper eventMapper;
    private final LocationMapper locationMapper;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<EventFullDto> getAll(List<Long> userIds, List<String> states, List<Long> catIds,
                                     String rangeStart, String rangeEnd, Integer from, Integer size) {
        Iterable<User> userIterable = userRepository.findAllById(userIds);
        List<User> users  = new ArrayList<>();
        for (User user : userIterable) {
            if (user != null) {
                users.add(user);
            }
        }
        if (users.isEmpty()) {
            throw new NotFindUsersException("Данных пользователей нет в базе");
        }

        List<Event> events = eventRepository.privateSearch(
                userIds,
                toStateEnums(states),
                catIds,
                rangeStart != null ? LocalDateTime.parse(rangeStart, dateTimeFormatter) : null,
                rangeEnd != null ? LocalDateTime.parse(rangeEnd, dateTimeFormatter) : null,
                from,
                size
                );

        if (events.size() == 0) {
            return Collections.emptyList();
        }

        log.info("ApiAdmin. Возвращена информация по событиям по следующим параметрам: " +
                        "userIds= {}, " +
                        "states= {}, " +
                        "catIds= {}, " +
                        "rangeStart= {} ," +
                        "rangeEnd= {} ," +
                        "from= {}, " +
                        "size= {}",
                userIds, states, catIds, rangeStart, rangeEnd, from, size);

        return events.stream().map(eventMapper::toEventFullDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto patchEvent(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFindEventException(eventId));

        checkOrUpdateEventDate(updateEventAdminRequest, event);
        checkState(updateEventAdminRequest, event);
        checkOrUpdateTitle(updateEventAdminRequest, event);
        checkOrUpdateAnnotation(updateEventAdminRequest, event);
        checkOrUpdateDescription(updateEventAdminRequest, event);
        checkOrUpdateCategory(updateEventAdminRequest, event);
        checkOrUpdateLocation(updateEventAdminRequest, event);
        checkOrUpdatePaid(updateEventAdminRequest, event);
        checkOrUpdateParticipantLimit(updateEventAdminRequest, event);
        checkOrUpdateRequestModeration(updateEventAdminRequest, event);

        eventRepository.save(event);
        log.info("ApiAdmin. Обновлено событие с id= {}", event.getId());
        return toEventFullDto(event);
    }

    private void checkOrUpdateEventDate(UpdateEventAdminRequest updateEventAdminRequest, Event event) {
        if (event.getEventDate().minusHours(1).isBefore(LocalDateTime.now())) {
            throw new TooLateEventException(event.getTitle());
        } else {
            if (updateEventAdminRequest.getEventDate() != null) {
                if (LocalDateTime.parse(updateEventAdminRequest.getEventDate(), dateTimeFormatter).minusHours(1)
                        .isBefore(LocalDateTime.now())) {
                    throw new TooLateEventException(updateEventAdminRequest.getTitle());
                } else {
                    event.setEventDate(LocalDateTime.parse(updateEventAdminRequest.getEventDate(), dateTimeFormatter));
                }
            }
        }
    }

    private void checkState(UpdateEventAdminRequest updateEventAdminRequest, Event event) {
            if (StateAction.valueOf(updateEventAdminRequest.getStateAction()) == StateAction.PUBLISH_EVENT) {
                if (event.getState() != State.PENDING) {
                    throw new NotPendingEventException("Only pending events can be changed");
                }
                event.setState(State.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            }

            if (StateAction.valueOf(updateEventAdminRequest.getStateAction()) == StateAction.REJECT_EVENT) {
                if (event.getState() == State.PUBLISHED) {
                    throw new PublishedEventException();
                }
                event.setState(State.CANCELED);
            }
    }

    private void checkOrUpdateTitle(UpdateEventAdminRequest updateEventAdminRequest, Event event) {
        if (updateEventAdminRequest.getTitle() != null) {
            event.setTitle(updateEventAdminRequest.getTitle());
        }
    }

    private void checkOrUpdateAnnotation(UpdateEventAdminRequest updateEventAdminRequest, Event event) {
        if (updateEventAdminRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventAdminRequest.getAnnotation());
        }
    }

    private void checkOrUpdateDescription(UpdateEventAdminRequest updateEventAdminRequest, Event event) {
        if (updateEventAdminRequest.getDescription() != null) {
            event.setDescription(updateEventAdminRequest.getDescription());
        }
    }

    private void checkOrUpdateCategory(UpdateEventAdminRequest updateEventAdminRequest, Event event) {
        if (updateEventAdminRequest.getCategory() != null) {
            Category category = categoryRepository.findById(updateEventAdminRequest.getCategory()).orElseThrow(() ->
                    new NotFindCategoryException(updateEventAdminRequest.getCategory()));
            event.setCategory(category);
        }
    }

    private void checkOrUpdateLocation(UpdateEventAdminRequest updateEventAdminRequest, Event event) {
        if (updateEventAdminRequest.getLocation() != null) {
            Long oldLocationId = event.getLocationModel().getId();
            LocationModel locationModel = locationRepository
                    .save(locationMapper.toLocationModel(oldLocationId, updateEventAdminRequest.getLocation()));
            event.setLocationModel(locationModel);
        }
    }

    private void checkOrUpdatePaid(UpdateEventAdminRequest updateEventAdminRequest, Event event) {
        if (updateEventAdminRequest.getPaid() != null) {
            event.setPaid(updateEventAdminRequest.getPaid());
        }
    }

    private void checkOrUpdateParticipantLimit(UpdateEventAdminRequest updateEventAdminRequest, Event event) {
        if (updateEventAdminRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
        }
    }

    private void checkOrUpdateRequestModeration(UpdateEventAdminRequest updateEventAdminRequest, Event event) {
        if (updateEventAdminRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventAdminRequest.getRequestModeration());
        }
    }

    private EventFullDto toEventFullDto (Event event) {
        return eventMapper.toEventFullDto(
                event
        );
    }

    private List<State> toStateEnums(List<String> state) {
        List<State> stateEnums = new ArrayList<>();
        if (state == null) {
            return Collections.emptyList();
        }
        for (String s : state) {
            stateEnums.add(State.valueOf(s));
        }
        return stateEnums;
    }
}
