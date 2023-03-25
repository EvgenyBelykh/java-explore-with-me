package ru.practicum.common.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.common.dto.*;
import ru.practicum.common.models.Event;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;
    private final LocationMapper locationMapper;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EventFullDto toEventFullDto(Event event) {
        return new EventFullDto(
                event.getId(),
                categoryMapper.toCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getCreatedOn().format(dateTimeFormatter),
                event.getEventDate().format(dateTimeFormatter),
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
                event.getViews()
        );
    }

    public EventShortDto toEventShortDto(Event event) {
        return new EventShortDto(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                categoryMapper.toCategoryDto(event.getCategory()),
                userMapper.toUserShortDto(event.getInitiator()),
                event.getEventDate().format(dateTimeFormatter),
                event.getConfirmedRequests(),
                event.getPaid(),
                event.getViews()
        );
    }
}
