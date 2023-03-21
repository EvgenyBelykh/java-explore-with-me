package ru.practicum.apiAdmin.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiAdmin.services.CompilationServiceAdmin;
import ru.practicum.common.dto.CompilationDto;
import ru.practicum.common.dto.NewCompilationDto;
import ru.practicum.common.dto.UpdateCompilationRequest;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
@Slf4j
public class CompilationControllerAdmin {

    private final CompilationServiceAdmin compilationServiceAdmin;

    @PostMapping
    public CompilationDto add(@Valid @RequestBody NewCompilationDto newCopmpilationDto) {

        log.info("Запрос добавления новой подборки событий с заголовком {}", newCopmpilationDto.getTitle());
        return compilationServiceAdmin.add(newCopmpilationDto);
    }

    @DeleteMapping("/{compId}")
    public void remove(@PathVariable(value = "compId") Long compId) {

        log.info("Запрос удаления подборки событий с id= {}", compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto patch(@PathVariable(value = "compId") Long compId,
                                @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {

        log.info("Запрос обновления подборки событий с id= {}", compId);
        return compilationServiceAdmin.patch(compId, updateCompilationRequest);
    }
}
