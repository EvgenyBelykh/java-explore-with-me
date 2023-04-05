package ru.practicum.apiPublic.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.common.dto.CommentDto;
import ru.practicum.common.exceptions.NotFindEventException;
import ru.practicum.common.mappers.CommentMapper;
import ru.practicum.common.models.Comment;
import ru.practicum.common.repositories.CommentRepository;
import ru.practicum.common.repositories.EventRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServicePublicImpl implements CommentServicePublic {
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Override
    public List<CommentDto> getAll(Long eventId, Integer from, Integer size) {
        eventRepository.findById(eventId).orElseThrow(() -> new NotFindEventException(eventId));

        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "event_id"));
        Page<Comment> commentPages = commentRepository.findAllByEventId(eventId, pageable);
        List<Comment> comments = commentPages.getContent();
        if (comments.size() == 0) {
            log.info("ApiPublic. Возвращен пустой список комментарией для события с id= {}", eventId);
            return Collections.emptyList();
        }
        log.info("ApiPublic. Возвращен список комментариев для события с id= {}", eventId);
        return comments.stream().map(commentMapper::toCommentDto).collect(Collectors.toList());
    }
}