package ru.practicum.common.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.common.dto.ParticipationRequestDto;
import ru.practicum.common.enums.Status;
import ru.practicum.common.models.Event;
import ru.practicum.common.models.ParticipationRequest;
import ru.practicum.common.models.User;

import java.time.LocalDateTime;

@Component
public class ParticipationRequestMapper {

    public ParticipationRequest toParticipationRequest(ParticipationRequestDto participationRequestDto,
                                                       LocalDateTime created,
                                                       Status status,
                                                       User requester,
                                                       Event event) {

        return new ParticipationRequest(
                participationRequestDto.getId(),
                created,
                status,
                requester,
                event
        );
    }

    public ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        return new ParticipationRequestDto(
                participationRequest.getId(),
                participationRequest.getRequester().getId(),
                participationRequest.getEvent().getId(),
                participationRequest.getStatus().toString(),
                participationRequest.getCreated().toString()
        );
    }
}
