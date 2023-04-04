package ru.practicum.common.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.practicum.dto.EndpointHitDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EndpointHitMapper {

    private final DateTimeMapper dateTimeMapper;
    @Value("${ewm-service.appName}")
    private String app;

    public EndpointHitDto toEndpointHitDto(HttpServletRequest request) {
        EndpointHitDto endpointHitDto = new EndpointHitDto();
        endpointHitDto.setApp(app);
        endpointHitDto.setIp(request.getRemoteAddr());
        endpointHitDto.setTimestamp(dateTimeMapper.toLocalDateTimeString(LocalDateTime.now()));
        endpointHitDto.setUri(request.getRequestURI());
        return endpointHitDto;
    }

}
