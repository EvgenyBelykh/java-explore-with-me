package ru.practicum.apiPublic.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.common.dto.CategoryDto;
import ru.practicum.common.exceptions.NotFindCategoryException;
import ru.practicum.common.mappers.CategoryMapper;
import ru.practicum.common.models.Category;
import ru.practicum.common.repositories.CategoryRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServicePublicPublicImpl implements CategoryServicePublic {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Category> categories = categoryRepository.findAll(pageable);
        if (categories.getContent().isEmpty()) {
            log.info("ApiPublic. Возвращен пустой список");
            return Collections.emptyList();
        }

        log.info("ApiPublic. Возвращены категории по параметрам from= {}, size= {}", from, size);
        return categories.stream().map(categoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto get(Long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() ->
                new NotFindCategoryException(catId));

        log.info("ApiPublic. Возвращены категория c id= {}", catId);
        return categoryMapper.toCategoryDto(category);
    }
}
