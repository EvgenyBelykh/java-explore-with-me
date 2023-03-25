package ru.practicum.apiPublic.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiPublic.services.CategoryServicePublic;
import ru.practicum.common.dto.CategoryDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Slf4j
public class CategoryControllerPublic {

    private final CategoryServicePublic categoryServicePublic;

    @GetMapping
    public List<CategoryDto> getAll(@RequestParam(value = "from", defaultValue = "0") Integer from,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("ApiPublic. Запрос получения категорий по параметрам from= {}, size= {}", from, size);
        return categoryServicePublic.getAll(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto get(@PathVariable(value = "catId") Long catId) {
        log.info("ApiPublic. Запрос получения категории c id= {}", catId);
        return categoryServicePublic.get(catId);
    }

}
