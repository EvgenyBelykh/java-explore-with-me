package ru.practicum.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventFullDto {
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String annotation;

    @NotBlank
    private String description;

    private CategoryDto category;

    @NotBlank
    private Location location;

    @NotBlank
    private UserShortDto initiator;

    @NotBlank
    private String eventDate;

    private String createdOn;
    //Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")

    private String publishedOn;
    //Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")

    private Long confirmedRequests;
    //Количество одобренных заявок на участие в данном событии

    private Boolean requestModeration;
    //Нужна ли пре-модерация заявок на участие.
    // Если true, то все заявки будут ожидать подтверждения инициатором события.
    // Если false - то будут подтверждаться автоматически.

    @NotBlank
    private Boolean paid;
    //Нужно ли оплачивать участие

    private Integer participantLimit;
    //Ограничение на количество участников. Значение 0 - означает отсутствие ограничения

    private String state;
    //Список состояний жизненного цикла события

    private Long views;
    //Количество просмотрев события

    public EventFullDto(Long id,
                        CategoryDto categoryDto,
                        Long confirmedRequests,
                        String createdOn,
                        String eventDate,
                        String description,
                        UserShortDto initiator,
                        Location location,
                        Boolean paid,
                        Integer participantLimit,
                        String publishedOn,
                        Boolean requestModeration,
                        String state,
                        String title,
                        String annotation,
                        Long views) {
        this.id = id;
        this.category = categoryDto;
        this.confirmedRequests = confirmedRequests;
        this.createdOn = createdOn;
        this.eventDate = eventDate;
        this.description = description;
        this.initiator = initiator;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.publishedOn = publishedOn;
        this.requestModeration = requestModeration;
        this.state = state;
        this.title = title;
        this.annotation = annotation;
        this.views = views;
    }
}
