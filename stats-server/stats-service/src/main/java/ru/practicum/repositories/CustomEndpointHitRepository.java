package ru.practicum.repositories;

import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface  CustomEndpointHitRepository {

    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
