package ru.practicum.apiPublic.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.common.dto.CompilationDto;
import ru.practicum.common.exceptions.NotFindCompilationException;
import ru.practicum.common.mappers.CompilationMapper;
import ru.practicum.common.models.Compilation;
import ru.practicum.common.repositories.CompilationRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompilationServicePublicImpl implements CompilationServicePublic {
    private final CompilationRepository compilationRepository;

    private final CompilationMapper compilationMapper;

    @Override
    public List<CompilationDto> getAll(Integer from, Integer size, Boolean pinned) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Compilation> compilations = compilationRepository.findAllByPinned(pinned, pageable);
        if (compilations.getContent().isEmpty()) {
            log.info("Возвращен пустой список");
            return Collections.emptyList();
        }

        log.info("Возвращены публичные подборки событий по параметрам from= {}, size= {}, pinned= {}",
                from, size, pinned);
        return compilations.stream().map(compilationMapper::toCompilationDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto get(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() ->
                new NotFindCompilationException(compId));
        log.info("Возвращена подборка событий c id= {}", compId);
        return compilationMapper.toCompilationDto(compilation);
    }
}
