package ru.practicum.apiAdmin.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private ResponseEntity<CategoryDto> add(@Valid @RequestBody NewCategoryDto newCategoryDto){
        log.info("ApiAdmin. Запрос добавления категории name {}", newCategoryDto.getName());
        return new ResponseEntity<>(categoryServiceAdmin.add(newCategoryDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{catId}")
    private ResponseEntity<Void> remove(@PathVariable("catId") Long idCat){
        log.info("ApiAdmin. Запрос удаления категории с id {}", idCat);
        categoryServiceAdmin.remove(idCat);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{catId}")
    private CategoryDto patch(@PathVariable("catId") Long idCat,
                              @RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("ApiAdmin. Запрос обновления категории с id {}", idCat);
        return categoryServiceAdmin.patch(idCat, newCategoryDto);
    }

}
