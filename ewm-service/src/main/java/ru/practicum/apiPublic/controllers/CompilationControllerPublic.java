package ru.practicum.apiPublic.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiPublic.services.CompilationServicePublic;
import ru.practicum.common.dto.CompilationDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
@Slf4j
public class CompilationControllerPublic {

    private final CompilationServicePublic compilationServicePublic;

    @GetMapping
    public List<CompilationDto> get(@RequestParam(value = "from", defaultValue = "0") Integer from,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size,
                                    @RequestParam(value = "pinned") Boolean pinned) {
        log.info("Запрос получения публичных подборок событий по параметрам from= {}, size= {}, pinned= {}",
                from, size, pinned);
        return compilationServicePublic.getAll(from, size, pinned);
    }

    @GetMapping("/{compId}")
    public CompilationDto get(@PathVariable(value = "compId") Long compId) {
        log.info("Запрос получения подборки событий c id= {}", compId);
        return compilationServicePublic.get(compId);
    }
}
