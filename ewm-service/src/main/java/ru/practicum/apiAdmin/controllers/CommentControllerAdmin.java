package ru.practicum.apiAdmin.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiAdmin.services.CommentServiceAdmin;
import ru.practicum.common.dto.CommentDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
@Slf4j
public class CommentControllerAdmin {
    private final CommentServiceAdmin commentServiceAdmin;

    @GetMapping("/{comId}")
    public CommentDto get(@PathVariable("comId") Long comId) {
        log.info("ApiAdmin. Запрос получения комментария с id= {}", comId);
        return commentServiceAdmin.get(comId);
    }

    @DeleteMapping("/{comId}")
    public ResponseEntity<Void> remove(@PathVariable("comId") Long comId) {
        log.info("ApiAdmin. Запрос удаления комментария c id= {}", comId);
        commentServiceAdmin.remove(comId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}