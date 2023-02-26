package ru.practicum.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;

@Component
public class EndpointHitMapper {
    public EndpointHitDto toEndpointHitDto(EndpointHit endpointHit) {
        return new EndpointHitDto(endpointHit.getApp(),
                endpointHit.getUri(),
                endpointHit.getIp(),
                endpointHit.getTimestamp().toString());
    }

    public EndpointHit toEndpointHit(String app, String uri, String ip, LocalDateTime timestamp) {
        return new EndpointHit(app,
                uri,
                ip,
                timestamp);
    }
}
