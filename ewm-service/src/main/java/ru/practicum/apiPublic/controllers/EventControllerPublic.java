package ru.practicum.apiPublic.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiPublic.services.EventServicePublic;
import ru.practicum.common.dto.EventFullDto;
import ru.practicum.common.dto.EventShortDto;
import ru.practicum.common.enums.SortEvents;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Slf4j
public class EventControllerPublic {
    private final EventServicePublic eventServicePublic;

    @GetMapping
    public List<EventShortDto> getAll(@RequestParam(value = "text", required = false) String text,
                                      @RequestParam(value = "categories", required = false) List<Long> categories,
                                      @RequestParam(value = "paid", defaultValue = "false") Boolean paid,

                                      @RequestParam(value = "rangeStart", required = false)
                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,

                                      @RequestParam(value = "rangeEnd", required = false)
                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,

                                      @RequestParam(value = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
                                      @RequestParam(value = "sort", defaultValue = "EVENT_DATE") SortEvents sort,
                                      @RequestParam(value = "from", defaultValue = "0") Integer from,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size,
                                      HttpServletRequest request) {

        log.info("Запрос получения событий по параметрам text= {}, categories= {}, paid= {}, rangeStart= {}, " +
                        "rangeEnd= {}, onlyAvailable= {}, sort= {}, from= {}, size= {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        return eventServicePublic.getAll(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size,
                request);
    }

    @GetMapping("/{id}")
    public EventFullDto get(@PathVariable(value = "id") Long id,
                            HttpServletRequest request) {

        log.info("Запрос получения подробной информации об опубликованном событии c id= {}", id);
        return eventServicePublic.get(id, request);
    }
}
