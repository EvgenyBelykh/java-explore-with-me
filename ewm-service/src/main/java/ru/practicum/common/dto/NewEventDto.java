package ru.practicum.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewEventDto {

    @Length(min = 3, max = 120, message = "Неверные данные: размер заголовка от 3 до 120 символов")
    @NotBlank
    private String title;

    @Length(min = 20, max = 2000, message = "Неверные данные: размер аннотации от 20 до 2000 символов")
    @NotBlank
    private String annotation;

    @Length(min = 20, max = 7000, message = "Неверные данные: размер описания от 20 до 7000 символов")
    @NotBlank
    private String description;

    @NotNull(message = "Необходимо задать категорию")
    private Long category;

    @NotBlank(message = "Необходимо задать дату события")
    private String eventDate;

    @NotNull(message = "Необходимо задать локацию")
    @Valid
    private Location location;

    private boolean paid;
    //Нужно ли оплачивать участие

    @PositiveOrZero
    private int participantLimit;
    //Ограничение на количество участников. Значение 0 - означает отсутствие ограничения

    private boolean requestModeration;
    //Нужна ли пре-модерация заявок на участие.
    // Если true, то все заявки будут ожидать подтверждения инициатором события.
    // Если false - то будут подтверждаться автоматически.
}
