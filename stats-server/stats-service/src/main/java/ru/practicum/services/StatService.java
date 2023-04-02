package ru.practicum.services;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;

import java.util.List;

public interface StatService {
    EndpointHitDto add(EndpointHitDto endpointHitDto);

    List<ViewStats> getStat(String start, String end, List<String> uris, Boolean unique);
}
