package ru.practicum.apiAdmin.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiAdmin.services.CategoryServiceAdmin;
import ru.practicum.common.dto.CategoryDto;
import ru.practicum.common.dto.NewCategoryDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
@Slf4j
public class CategoryControllerAdmin {
    private final CategoryServiceAdmin categoryServiceAdmin;

    @PostMapping
    private CategoryDto add(@Valid @RequestBody NewCategoryDto newCategoryDto){
        log.info("Запрос добавления категории name {}", newCategoryDto.getName());
        return categoryServiceAdmin.add(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    private void remove(@PathVariable("catId") Long idCat){
        log.info("Запрос удаления категории с id {}", idCat);
        categoryServiceAdmin.remove(idCat);
    }

    @PatchMapping("/{catId}")
    private CategoryDto patch(@PathVariable("catId") Long idCat,
                              @RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("Запрос обновления категории с id {}", idCat);
        return categoryServiceAdmin.patch(idCat, newCategoryDto);
    }

}
