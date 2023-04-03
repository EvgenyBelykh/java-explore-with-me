package ru.practicum.common.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.common.dto.ParticipationRequestDto;
import ru.practicum.common.models.ParticipationRequest;

@Component
@RequiredArgsConstructor
public class ParticipationRequestMapper {

    private final DateTimeMapper dateTimeMapper;

    public ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        return new ParticipationRequestDto(
                participationRequest.getId(),
                participationRequest.getRequester().getId(),
                participationRequest.getEvent().getId(),
                participationRequest.getStatus().toString(),
                participationRequest.getCreated() != null ?
                        dateTimeMapper.toLocalDateTimeString(participationRequest.getCreated()) : null
        );
    }
}
