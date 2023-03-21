package ru.practicum.apiAdmin.services;

import ru.practicum.common.dto.CategoryDto;
import ru.practicum.common.dto.NewCategoryDto;

public interface CategoryServiceAdmin {
    CategoryDto add(NewCategoryDto newCategoryDto);

    void remove(Long idCat);

    CategoryDto patch(Long idCat, NewCategoryDto newCategoryDto);
}
