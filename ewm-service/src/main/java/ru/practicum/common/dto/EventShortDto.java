package ru.practicum.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventShortDto {
    private Long id;

    private String title;

    private String annotation;

    private CategoryDto category;

    private UserShortDto initiator;

    private String eventDate;

    private Long confirmedRequests;
    //Количество одобренных заявок на участие в данном событии

    private Boolean paid;
    //Нужно ли оплачивать участие

    private Long views;
    //Количество просмотрев события
}
