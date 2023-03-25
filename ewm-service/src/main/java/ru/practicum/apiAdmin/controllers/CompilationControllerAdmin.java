package ru.practicum.apiAdmin.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CompilationDto> add(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Запрос добавления новой подборки событий с заголовком {}", newCompilationDto.getTitle());
        return new ResponseEntity<>(compilationServiceAdmin.add(newCompilationDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Void> remove(@PathVariable(value = "compId") Long compId) {

        log.info("ApiAdmin. Запрос удаления подборки событий с id= {}", compId);
        compilationServiceAdmin.remove(compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{compId}")
    public CompilationDto patch(@PathVariable(value = "compId") Long compId,
                                @RequestBody UpdateCompilationRequest updateCompilationRequest) {

        log.info("ApiAdmin. Запрос обновления подборки событий с id= {}", compId);
        return compilationServiceAdmin.patch(compId, updateCompilationRequest);
    }
}
