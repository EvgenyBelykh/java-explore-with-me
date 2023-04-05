package ru.practicum.apiAdmin.services;

import ru.practicum.common.dto.CommentDto;

import java.util.List;

public interface CommentServiceAdmin {
    CommentDto get(Long comId);

    void remove(Long comId);
}
