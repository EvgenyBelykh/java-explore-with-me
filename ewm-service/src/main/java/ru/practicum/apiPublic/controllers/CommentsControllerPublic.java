package ru.practicum.apiPublic.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiPublic.services.CommentServicePublic;
import ru.practicum.common.dto.CommentDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Slf4j
public class CommentsControllerPublic {
    private final CommentServicePublic commentServicePublic;

    @GetMapping("/{eventId}")
    public List<CommentDto> getAll(@PathVariable("eventId") Long eventId,
                                   @RequestParam(value = "from", defaultValue = "0") Integer from,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("ApiPublic. Запрос получения всех комментариев событию с id= {}", eventId);
        return commentServicePublic.getAll(eventId, from, size);
    }
}