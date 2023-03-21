package ru.practicum.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCompilationDto {
    @NotBlank(message = "Заголовок не может быть пустым")
    @Min(value = 3, message = "Неверные данные: размер заголовка от 3 до 120 символов")
    @Max(value = 120, message = "Неверные данные: размер заголовка от 3 до 120 символов")
    private String title;

    private Boolean pinned;

    private List<Long> events;
}
