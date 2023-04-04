package ru.practicum.apiAdmin.services;

import ru.practicum.common.dto.EventFullDto;
import ru.practicum.common.dto.UpdateEventAdminRequest;

import java.util.List;

public interface EventServiceAdmin {
    List<EventFullDto> getAll(List<Long> userIds, List<String> states, List<Long> catIds,
                              String rangeStart, String rangeEnd, Integer from, Integer size);

    EventFullDto patchEvent(Long eventId, UpdateEventAdminRequest updateEventAdminRequest);
}
