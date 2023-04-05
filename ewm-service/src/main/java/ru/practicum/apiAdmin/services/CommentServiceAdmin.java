package ru.practicum.apiAdmin.services;

import ru.practicum.common.dto.CommentDto;

public interface CommentServiceAdmin {
    CommentDto get(Long comId);

    void remove(Long comId);
}
