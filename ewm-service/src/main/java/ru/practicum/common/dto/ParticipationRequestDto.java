package ru.practicum.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipationRequestDto {
    private Long id;
    //Идентификатор заявки

    private Long requester;
    //Идентификатор пользователя, отправившего заявку

    private Long event;
    //Идентификатор события

    private String status;
    //Статус заявки

    private String created;
    //Дата и время создания заявки

}
