package ru.practicum.apiAdmin.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.dto.CompilationDto;
import ru.practicum.common.dto.NewCompilationDto;
import ru.practicum.common.dto.UpdateCompilationRequest;
import ru.practicum.common.exceptions.NotFindCompilationException;
import ru.practicum.common.mappers.CompilationMapper;
import ru.practicum.common.models.Compilation;
import ru.practicum.common.models.Event;
import ru.practicum.common.repositories.CompilationRepository;
import ru.practicum.common.repositories.EventRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CompilationServiceAdminImpl implements CompilationServiceAdmin {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto add(NewCompilationDto newCompilationDto) {

        Iterable<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());
        Set<Event> eventSet = new HashSet<>();
        for (Event event : events) {
            eventSet.add(event);
        }

        Compilation compilation = compilationRepository
                .save(compilationMapper.toCompilation(newCompilationDto, eventSet));

        log.info("Сохранена подборка событий с заголовком {}", newCompilationDto.getTitle());
        return compilationMapper.toCompilationDto(compilation);
    }

    @Override
    public void remove(Long compId) {
        compilationRepository.findById(compId).orElseThrow(() -> new NotFindCompilationException(compId));
        compilationRepository.deleteById(compId);
        log.info("Удалена подборка событий с id= {}", compId);
    }

    @Override
    public CompilationDto patch(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFindCompilationException(compId));

        if(updateCompilationRequest.getTitle() != null) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }

        if(updateCompilationRequest.getPinned() != null) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }

        if(updateCompilationRequest.getEvents() != null) {
            Iterable<Event> events = eventRepository.findAllById(updateCompilationRequest.getEvents());
            Set<Event> eventSet = new HashSet<>();
            for (Event event : events) {
                eventSet.add(event);
            }
            compilation.setEvents(eventSet);
        }

        compilation = compilationRepository.save(compilation);

        log.info("Обновлена подборка событий с id= {}", compId);
        return compilationMapper.toCompilationDto(compilation);
    }


}
