package ru.practicum.apiPrivate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiPrivate.services.CommentServicePrivate;
import ru.practicum.common.dto.CommentDto;
import ru.practicum.common.dto.NewCommentDto;
import ru.practicum.common.dto.UpdateCommentDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/comments")
@Slf4j
public class CommentControllerPrivate {
    private final CommentServicePrivate commentServicePrivate;

    @PostMapping("/{eventId}")
    public ResponseEntity<CommentDto> add(@PathVariable("userId") Long userId,
                          @PathVariable("eventId") Long eventId,
                          @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info("ApiPrivate. Запрос добавления комментария от пользователя с id= {} событию c id= {}", userId, eventId);
        return new ResponseEntity<>(commentServicePrivate.add(userId, eventId, newCommentDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{comId}")
    public CommentDto patch(@PathVariable("userId") Long userId,
                            @PathVariable("comId") Long comId,
                            @RequestBody @Valid UpdateCommentDto updateCommentDto) {
        log.info("ApiPrivate. Запрос обновления комментария от пользователя с id= {} комментария c id= {}", userId, comId);
        return commentServicePrivate.patch(userId, comId, updateCommentDto);
    }

    @DeleteMapping("/{comId}")
    public ResponseEntity<Void> remove(@PathVariable("userId") Long userId,
                                       @PathVariable("comId") Long comId) {
        log.info("ApiPrivate. Запрос удаления от пользователя с id= {} комментария c id= {}", userId, comId);
        commentServicePrivate.remove(userId, comId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public List<CommentDto> getAll(@PathVariable("userId") Long userId,
                                   @RequestParam(value = "from", defaultValue = "0") Integer from,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("ApiPrivate. Запрос получения всех комментариев от пользователя с id= {}", userId);
        return commentServicePrivate.getAll(userId, from, size);
    }
}