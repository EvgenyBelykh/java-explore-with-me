package ru.practicum.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompilationDto {
    @NotNull
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private Boolean pinned;

    private List<EventShortDto> events;
}
