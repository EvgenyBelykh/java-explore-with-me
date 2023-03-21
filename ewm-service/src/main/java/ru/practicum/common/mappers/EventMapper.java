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


    public Event toEvent(NewEventDto newEventDto,
                         Category category,
                         LocationModel locationModel,
                         User initiator,
                         LocalDateTime eventDate,
                         LocalDateTime createdOn,
                         State state,
                         Long views) {
        return new Event(
                newEventDto.getTitle(),
                newEventDto.getAnnotation(),
                newEventDto.getDescription(),
                category,
                locationModel,
                initiator,
                eventDate,
                createdOn,
                newEventDto.getRequestModeration(),
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit(),
                state,
                views
        );
    }

//    public EventFullDto toEventFullDto(Event event,
//                                       CategoryDto categoryDto,
//                                       UserShortDto userShortDto,
//                                       Location location) {
//        return new EventFullDto(
//                event.getId(),
//                categoryDto,
//                event.getConfirmedRequests(),
//                event.getCreatedOn(),
//                event.getDescription(),
//                userShortDto,
//                location,
//                event.getPaid(),
//                event.getParticipantLimit(),
//                event.getPublishedOn(),
//                event.getRequestModeration(),
//                event.getState(),
//                event.getTitle(),
//                event.getViews()
//                );
//    }

    public EventFullDto toEventFullDto(Event event) {
        return new EventFullDto(
                event.getId(),
                categoryMapper.toCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getCreatedOn(),
                event.getDescription(),
                userMapper.toUserShortDto(event.getInitiator()),
                event.getLocationModel() != null ? locationMapper.toLocation(event.getLocationModel()) : null,
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn() != null ? event.getPublishedOn().toString() : null,
                event.getRequestModeration(),
                event.getState().toString(),
                event.getTitle(),
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
                event.getEventDate().toString(),
                event.getConfirmedRequests(),
                event.getPaid(),
                event.getViews()
        );
    }
}
