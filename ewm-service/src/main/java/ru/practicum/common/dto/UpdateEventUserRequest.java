package ru.practicum.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEventUserRequest {
    @Min(value = 3, message = "Неверные данные: размер заголовка от 3 до 120 символов")
    @Max(value = 120, message = "Неверные данные: размер заголовка от 3 до 120 символов")
    private String title;

    @Min(value = 20, message = "Неверные данные: размер аннотации от 20 до 2000 символов")
    @Max(value = 2000, message = "Неверные данные: размер аннотации от 20 до 2000 символов")
    private String annotation;

    @Min(value = 20, message = "Неверные данные: размер описания от 20 до 7000 символов")
    @Max(value = 7000, message = "Неверные данные: размер описания от 20 до 7000 символов")
    private String description;

    private Long category;

    private String eventDate;

    private Location location;

    private Boolean paid;
    //Нужно ли оплачивать участие

    private Integer participantLimit;
    //Ограничение на количество участников. Значение 0 - означает отсутствие ограничения

    private Boolean requestModeration;
    //Нужна ли пре-модерация заявок на участие.
    // Если true, то все заявки будут ожидать подтверждения инициатором события.
    // Если false - то будут подтверждаться автоматически.

    private String stateAction;
    //Список состояний жизненного цикла события
}
