package ru.practicum.apiAdmin.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.dto.CategoryDto;
import ru.practicum.common.dto.NewCategoryDto;
import ru.practicum.common.exceptions.EventUseThisCategoryException;
import ru.practicum.common.exceptions.ExistNameCategoryException;
import ru.practicum.common.exceptions.NotFindCategoryException;
import ru.practicum.common.mappers.CategoryMapper;
import ru.practicum.common.models.Category;
import ru.practicum.common.repositories.CategoryRepository;
import ru.practicum.common.repositories.EventRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryServiceAdminImpl implements CategoryServiceAdmin {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto add(NewCategoryDto newCategoryDto) {
        isNameAlreadyUse(newCategoryDto);

        Category category = categoryRepository.save(categoryMapper.toCategory(newCategoryDto));
        log.info("ApiAdmin. Сохранена категория name= {}", newCategoryDto.getName());

        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public void remove(Long idCat) {

        if (eventRepository.existsByCategoryId(idCat)) {
            throw new EventUseThisCategoryException();
        }

        categoryRepository.findById(idCat).orElseThrow(() -> new NotFindCategoryException(idCat));
        categoryRepository.deleteById(idCat);
        log.info("ApiAdmin. Удалена категория с id={}", idCat);
    }

    @Override
    public CategoryDto patch(Long idCat, NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.findById(idCat).orElseThrow(() -> new NotFindCategoryException(idCat));
        Category ExistCategory = categoryRepository.findByName(newCategoryDto.getName());

        if (ExistCategory != null && ExistCategory.getId() != idCat) {
            throw new ExistNameCategoryException(newCategoryDto.getName());
        }

        category.setName(newCategoryDto.getName());
        log.info("ApiAdmin. Обновлена категория с id={} и name={}", idCat, category.getName());

        return categoryMapper.toCategoryDto(category);
    }

    private void isNameAlreadyUse(NewCategoryDto newCategoryDto) {
        if (categoryRepository.existsByNameContaining(newCategoryDto.getName())) {
            throw new ExistNameCategoryException(newCategoryDto.getName());
        }
    }
}
