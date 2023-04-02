package ru.practicum.common.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.common.dto.*;
import ru.practicum.common.enums.State;
import ru.practicum.common.models.Category;
import ru.practicum.common.models.Event;
import ru.practicum.common.models.LocationModel;
import ru.practicum.common.models.User;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;
    private final LocationMapper locationMapper;
    private final DateTimeMapper dateTimeMapper;

    public Event toEvent(
            NewEventDto newEventDto,
            Category category,
            LocationModel locationModel,
            User initiator,
            LocalDateTime createdOn,
            String publishedOn,
            Long confirmedRequest,
            State state) {
        return new Event(
                newEventDto.getTitle(),
                newEventDto.getAnnotation(),
                newEventDto.getDescription(),
                category,
                locationModel,
                initiator,
                dateTimeMapper.toLocalDateTime(newEventDto.getEventDate()),
                createdOn,
                publishedOn != null ? dateTimeMapper.toLocalDateTime(publishedOn) : null,
                confirmedRequest,
                newEventDto.isRequestModeration(),
                newEventDto.isPaid(),
                newEventDto.getParticipantLimit(),
                state
                );
    }

    public EventFullDto toEventFullDto(Event event, Long views) {
        return new EventFullDto(
                event.getId(),
                categoryMapper.toCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                dateTimeMapper.toLocalDateTimeString(event.getCreatedOn()),
                dateTimeMapper.toLocalDateTimeString(event.getEventDate()),
                event.getDescription(),
                userMapper.toUserShortDto(event.getInitiator()),
                event.getLocationModel() != null ? locationMapper.toLocation(event.getLocationModel()) : null,
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn() != null ? event.getPublishedOn().toString() : null,
                event.getRequestModeration(),
                event.getState().toString(),
                event.getTitle(),
                event.getAnnotation(),
                views
        );
    }

    public EventShortDto toEventShortDto(Event event, Long views) {
        return new EventShortDto(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                categoryMapper.toCategoryDto(event.getCategory()),
                userMapper.toUserShortDto(event.getInitiator()),
                dateTimeMapper.toLocalDateTimeString(event.getEventDate()),
                event.getConfirmedRequests(),
                event.getPaid(),
                views
        );
    }
}
