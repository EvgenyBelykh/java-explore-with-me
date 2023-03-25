package ru.practicum.common.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.common.dto.ParticipationRequestDto;
import ru.practicum.common.models.ParticipationRequest;

import java.time.format.DateTimeFormatter;

@Component
public class ParticipationRequestMapper {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        return new ParticipationRequestDto(
                participationRequest.getId(),
                participationRequest.getRequester().getId(),
                participationRequest.getEvent().getId(),
                participationRequest.getStatus().toString(),
                participationRequest.getCreated() != null ? participationRequest.getCreated().format(dateTimeFormatter) : null
        );
    }
}
