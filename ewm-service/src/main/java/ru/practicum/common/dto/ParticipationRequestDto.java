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
public class ParticipationRequestDto {
    @NotBlank
    private Long id;
    //Идентификатор заявки

    @NotBlank
    private Long requester;
    //Идентификатор пользователя, отправившего заявку

    @NotBlank
    private Long event;
    //Идентификатор события

    @NotBlank
    private String status;
    //Статус заявки

    @NotBlank
    private String created;
    //Дата и время создания заявки

}
