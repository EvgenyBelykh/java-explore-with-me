package ru.practicum.apiPublic.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.StatsClient;
import ru.practicum.common.dto.EventFullDto;
import ru.practicum.common.dto.EventShortDto;
import ru.practicum.common.enums.SortEvents;
import ru.practicum.common.enums.State;
import ru.practicum.common.exceptions.NotFindEventException;
import ru.practicum.common.exceptions.NotPublishedPublicEventException;
import ru.practicum.common.mappers.EventMapper;
import ru.practicum.common.models.Event;
import ru.practicum.common.repositories.EventRepository;
import ru.practicum.dto.EndpointHitDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServicePublicImpl implements EventServicePublic {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    private final StatsClient statsClient;

    @Override
    public List<EventShortDto> getAll(String text, List<Long> categories, Boolean paid,
                                      LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                      Boolean onlyAvailable, SortEvents sort, Integer from, Integer size,
                                      HttpServletRequest request) {

        statsClient.hit(EndpointHitDto.builder()
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .build());


        List<Event> events = eventRepository.publicSearch(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);

        if (events.size() == 0) {
            log.info("Возвращен пустой список при поиске событий по параметрам text= {}, categories= {}, paid= {}, " +
                            "rangeStart= {}, rangeEnd= {}, onlyAvailable= {}, sort= {}, from= {}, size= {}",
                    text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);

            return Collections.emptyList();
        }

        log.info("Возвращены события по параметрам text= {}, categories= {}, paid= {}, rangeStart= {}, " +
                        "rangeEnd= {}, onlyAvailable= {}, sort= {}, from= {}, size= {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);

        return events.stream().map(eventMapper::toEventShortDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto get(Long id, HttpServletRequest request) {
        Event event = eventRepository.findById (id).orElseThrow(() -> new NotFindEventException(id));
        if (event.getState() != State.PUBLISHED) {
            throw new NotPublishedPublicEventException(id);
        }

        statsClient.hit(EndpointHitDto.builder()
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .build());

        log.info("Возвращен запрос получения подробной информации об опубликованном событии c id= {}", id);
        return eventMapper.toEventFullDto(event);
    }
}
