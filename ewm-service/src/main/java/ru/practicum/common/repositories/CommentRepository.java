package ru.practicum.common.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.common.models.Comment;
import ru.practicum.common.models.Event;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByAuthorId(Long userId, Pageable pageable);
    Page<Comment> findAllByEventId(Long userId, Pageable pageable);
}
