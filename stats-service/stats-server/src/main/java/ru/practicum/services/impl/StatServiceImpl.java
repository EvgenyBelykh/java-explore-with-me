package ru.practicum.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.mappers.EndpointHitMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;
import ru.practicum.repositories.EndpointHitRepository;
import ru.practicum.services.StatService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatServiceImpl implements StatService {
    private final EndpointHitRepository endpointHitRepository;
    private final EndpointHitMapper endpointHitMapper;
    DateTimeFormatter DATETIMEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public EndpointHitDto add(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = endpointHitMapper.toEndpointHit(endpointHitDto.getApp(),
                endpointHitDto.getUri(),
                endpointHitDto.getIp(),
                LocalDateTime.parse(endpointHitDto.getTimestamp(), DATETIMEFORMATTER));

        log.info("Сохранение информации об отправке запроса пользователем с ip={} на адрес={} ",
                endpointHitDto.getIp(), endpointHitDto.getUri());

        return endpointHitMapper.toEndpointHitDto(endpointHitRepository.save(endpointHit));

    }

    @Override
    public List<ViewStats> getStat(String start, String end, List<String> uris, Boolean unique) {
        return endpointHitRepository.getStats(
                LocalDateTime.parse(start, DATETIMEFORMATTER),
                LocalDateTime.parse(end, DATETIMEFORMATTER),
                uris,
                unique
        );
    }
}
