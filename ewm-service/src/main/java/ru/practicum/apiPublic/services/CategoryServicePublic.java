package ru.practicum.apiPublic.services;

import ru.practicum.common.dto.CategoryDto;

import java.util.List;

public interface CategoryServicePublic {
    List<CategoryDto> getAll(Integer from, Integer size);

    CategoryDto get(Long catId);
}
