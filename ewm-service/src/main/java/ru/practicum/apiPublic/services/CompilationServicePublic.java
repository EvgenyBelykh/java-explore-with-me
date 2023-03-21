package ru.practicum.apiPublic.services;

import ru.practicum.common.dto.CompilationDto;

import java.util.List;

public interface CompilationServicePublic {
    List<CompilationDto> getAll(Integer from, Integer size, Boolean pinned);

    CompilationDto get(Long compId);
}
