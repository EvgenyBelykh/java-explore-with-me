package ru.practicum.apiAdmin.services;

import ru.practicum.common.dto.CompilationDto;
import ru.practicum.common.dto.NewCompilationDto;
import ru.practicum.common.dto.UpdateCompilationRequest;

public interface CompilationServiceAdmin {
    CompilationDto add(NewCompilationDto newCopmpilationDto);

    void remove(Long compId);

    CompilationDto patch(Long compId, UpdateCompilationRequest updateCompilationRequest);
}
