package ru.practicum.common.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.common.dto.CompilationDto;
import ru.practicum.common.dto.NewCompilationDto;
import ru.practicum.common.models.Compilation;
import ru.practicum.common.models.Event;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper {
    private final EventMapper eventMapper;

    public Compilation toCompilation(NewCompilationDto newCompilationDto, List<Event> events) {
        return new Compilation(
                newCompilationDto.getTitle(),
                newCompilationDto.getPinned(),
                events
        );
    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                compilation.getPinned(),
                compilation.getEvents()
                        .stream().map(eventMapper::toEventShortDto).collect(Collectors.toList()));
    }
}
