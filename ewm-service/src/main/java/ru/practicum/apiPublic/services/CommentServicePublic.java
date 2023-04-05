package ru.practicum.apiPublic.services;

import ru.practicum.common.dto.CommentDto;

import java.util.List;

public interface CommentServicePublic {
    List<CommentDto> getAll(Long eventId, Integer from, Integer size);
}
