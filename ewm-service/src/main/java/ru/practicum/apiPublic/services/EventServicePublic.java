package ru.practicum.apiPublic.services;

import ru.practicum.common.dto.EventFullDto;
import ru.practicum.common.dto.EventShortDto;
import ru.practicum.common.enums.SortEvents;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventServicePublic {
    List<EventShortDto> getAll(String text, List<Long> categories, Boolean paid,
                               LocalDateTime rangeStart, LocalDateTime rangeEnd,
                               Boolean onlyAvailable, SortEvents sort, Integer from, Integer size,
                               HttpServletRequest request);

    EventFullDto get(Long id, HttpServletRequest request);
}
