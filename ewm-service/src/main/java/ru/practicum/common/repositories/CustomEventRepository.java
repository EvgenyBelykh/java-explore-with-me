package ru.practicum.common.repositories;

import ru.practicum.common.enums.SortEvents;
import ru.practicum.common.enums.State;
import ru.practicum.common.models.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomEventRepository {
    List<Event> publicSearch(
            String text, List<Long> categories, Boolean paid,
            LocalDateTime rangeStart, LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            SortEvents sort, Integer from, Integer size
    );

    List<Event> privateSearch(
            List<Long> userIds, List<State> states, List<Long> catIds,
            LocalDateTime rangeStart, LocalDateTime rangeEnd,
            Integer from, Integer size
    );
}
