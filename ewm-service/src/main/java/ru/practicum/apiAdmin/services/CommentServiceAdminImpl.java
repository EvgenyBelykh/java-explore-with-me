package ru.practicum.apiAdmin.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.dto.CommentDto;
import ru.practicum.common.exceptions.NotFindCommentsException;
import ru.practicum.common.mappers.CommentMapper;
import ru.practicum.common.models.Comment;
import ru.practicum.common.repositories.CommentRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentServiceAdminImpl implements CommentServiceAdmin {
    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Override
    public CommentDto get(Long comId) {
        Comment comment = commentRepository.findById(comId).orElseThrow(() -> new NotFindCommentsException(comId));
        log.info("ApiAdmin. Возвращен комментарий с id= {}", comId);
        return commentMapper.toCommentDto(comment);
    }

    @Override
    public void remove(Long comId) {
        commentRepository.findById(comId).orElseThrow(() -> new NotFindCommentsException(comId));
        commentRepository.deleteById(comId);
        log.info("ApiAdmin. Удален комментарий c id= {}", comId);
    }
}
