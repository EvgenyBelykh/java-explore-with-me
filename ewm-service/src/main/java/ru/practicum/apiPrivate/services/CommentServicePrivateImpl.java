package ru.practicum.apiPrivate.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.dto.CommentDto;
import ru.practicum.common.dto.NewCommentDto;
import ru.practicum.common.dto.UpdateCommentDto;
import ru.practicum.common.enums.State;
import ru.practicum.common.exceptions.*;
import ru.practicum.common.mappers.CommentMapper;
import ru.practicum.common.models.Comment;
import ru.practicum.common.models.Event;
import ru.practicum.common.models.User;
import ru.practicum.common.repositories.CommentRepository;
import ru.practicum.common.repositories.EventRepository;
import ru.practicum.common.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentServicePrivateImpl implements CommentServicePrivate {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Override
    public CommentDto add(Long userId, Long eventId, NewCommentDto newCommentDto) {
        User author = userRepository.findById(userId).orElseThrow(() -> new NotFindUserException(userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFindEventException(eventId));

        if (event.getState() != State.PUBLISHED) {
            throw new NotPublishedPrivateEventException("Event must be published");
        }
        Comment comment = commentRepository.save(commentMapper.toComment(newCommentDto, author, event));
        log.info("ApiPrivate. Сохранен комментарий от пользователя с id= {} событию c id= {}", userId, eventId);
        return commentMapper.toCommentDto(comment);
    }

    @Override
    public CommentDto patch(Long userId, Long comId, UpdateCommentDto updateCommentDto) {
        userRepository.findById(userId).orElseThrow(() -> new NotFindUserException(userId));
        Comment comment = commentRepository.findById(comId).orElseThrow(() -> new NotFindCommentsException(comId));
        if (comment.getAuthor().getId() != userId) {
            throw new NotBelongCommentToUserException(comId, userId);
        }
        comment.setText(updateCommentDto.getText());
        log.info("ApiPrivate. Обновлен комментарий от пользователя с id= {} событию c id= {}", userId, comment.getId());
        return commentMapper.toCommentDto(comment);
    }

    @Override
    public void remove(Long userId, Long comId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFindUserException(userId));
        Comment comment = commentRepository.findById(comId).orElseThrow(() -> new NotFindCommentsException(comId));
        if (comment.getAuthor().getId() != userId) {
            throw new NotBelongCommentToUserException(comId, userId);
        }
        commentRepository.deleteById(comId);
        log.info("ApiPrivate. Удален комментарий от пользователя с id= {} событию c id= {}", userId, comment.getId());
    }

    @Override
    public List<CommentDto> getAll(Long userId, Integer from, Integer size) {
        userRepository.findById(userId).orElseThrow(() -> new NotFindUserException(userId));

        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "created"));
        Page<Comment> commentPages = commentRepository.findAllByAuthorId(userId, pageable);
        List<Comment> comments = commentPages.getContent();
        if (comments.size() == 0) {
            log.info("ApiPrivate. Возвращен пустой список комментарией пользователя с id= {}", userId);
            return Collections.emptyList();
        }
        log.info("ApiPrivate. Возвращен список комментариев пользователя с id= {}", userId);
        return comments.stream().map(commentMapper::toCommentDto).collect(Collectors.toList());
    }
}