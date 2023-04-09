package ru.practicum.common.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.common.dto.CommentDto;
import ru.practicum.common.dto.NewCommentDto;
import ru.practicum.common.models.Comment;
import ru.practicum.common.models.Event;
import ru.practicum.common.models.User;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final DateTimeMapper dateTimeMapper;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;

    public Comment toComment(NewCommentDto newCommentDto, User author, Event event) {
        return new Comment(
                newCommentDto.getText(),
                author,
                event,
                LocalDateTime.now());
    }

    public CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                eventMapper.toEventShortDto(comment.getEvent(), null),
                userMapper.toUserShortDto(comment.getAuthor()),
                dateTimeMapper.toLocalDateTimeString(comment.getCreated())
        );
    }
}
