package ru.practicum.apiPrivate.services;

import ru.practicum.common.dto.CommentDto;
import ru.practicum.common.dto.NewCommentDto;
import ru.practicum.common.dto.UpdateCommentDto;

import java.util.List;

public interface CommentServicePrivate {
    CommentDto add(Long userId, Long eventId, NewCommentDto newCommentDto);

    CommentDto patch(Long userId, Long comId, UpdateCommentDto updateCommentDto);

    void remove(Long userId, Long comId);

    List<CommentDto> getAll(Long userId, Integer from, Integer size);
}
