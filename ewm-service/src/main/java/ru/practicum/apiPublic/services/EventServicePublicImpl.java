package ru.practicum.apiPublic.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatsClient;
import ru.practicum.common.dto.EventFullDto;
import ru.practicum.common.dto.EventShortDto;
import ru.practicum.common.enums.SortEvents;
import ru.practicum.common.enums.State;
import ru.practicum.common.exceptions.NotFindEventException;
import ru.practicum.common.exceptions.NotPublishedPublicEventException;
import ru.practicum.common.mappers.EndpointHitMapper;
import ru.practicum.common.mappers.EventMapper;
import ru.practicum.common.models.Event;
import ru.practicum.common.repositories.EventRepository;
import ru.practicum.dto.ViewStats;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EventServicePublicImpl implements EventServicePublic {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final EndpointHitMapper endpointHitMapper;
    private final StatsClient statsClient;

    @Override
    public List<EventShortDto> getAll(String text, List<Long> categories, Boolean paid,
                                      LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                      Boolean onlyAvailable, SortEvents sort, Integer from, Integer size,
                                      HttpServletRequest request) {

        statsClient.hit(endpointHitMapper.toEndpointHitDto(request));


        List<Event> events = eventRepository.publicSearch(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);

        if (events.size() == 0) {
            log.info("ApiPublic. Возвращен пустой список при поиске событий по параметрам text= {}, categories= {}, paid= {}, " +
                            "rangeStart= {}, rangeEnd= {}, onlyAvailable= {}, sort= {}, from= {}, size= {}",
                    text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);

            return Collections.emptyList();
        }

        log.info("ApiPublic. Возвращены события по параметрам text= {}, categories= {}, paid= {}, rangeStart= {}, " +
                        "rangeEnd= {}, onlyAvailable= {}, sort= {}, from= {}, size= {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        Set<Long> eventIds = events.stream().map(Event::getId).collect(Collectors.toSet());

        List<ViewStats> viewStatsList = statsClient.getViewsBySetEventId(eventIds);
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            eventShortDtoList.add(eventMapper.toEventShortDto(events.get(i),
                    viewStatsList != null && !viewStatsList.isEmpty() && viewStatsList.get(i) == null ?
                            viewStatsList.get(i).getHits() : 0L));
        }
        return eventShortDtoList;
    }

    @Override
    public EventFullDto get(Long id, HttpServletRequest request) {
        statsClient.hit(endpointHitMapper.toEndpointHitDto(request));

        Event event = eventRepository.findById(id).orElseThrow(() -> new NotFindEventException(id));
        if (event.getState() != State.PUBLISHED) {
            throw new NotPublishedPublicEventException(id);
        }

        Long views = statsClient.getViewsByEventId(id);

        log.info("ApiPublic. Возвращен запрос получения подробной информации об опубликованном событии c id= {}", id);
        return eventMapper.toEventFullDto(event, views);
    }
}
