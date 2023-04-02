package ru.practicum.apiAdmin.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiAdmin.services.EventServiceAdmin;
import ru.practicum.common.dto.EventFullDto;
import ru.practicum.common.dto.UpdateEventAdminRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@Slf4j
@RequiredArgsConstructor
@Validated
public class EventControllerAdmin {

    private final EventServiceAdmin eventServiceAdmin;

    @GetMapping
    public List<EventFullDto> getAll(@RequestParam(value = "users", required = false) List<Long> userIds,
                                     @RequestParam(value = "states", required = false) List<String> states,
                                     @RequestParam(value = "categories", required = false) List<Long> catIds,
                                     @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                     @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                     @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                     @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {

        log.info("ApiAdmin. Запрос получения информации по событиям по следующим параметрам: " +
                "userIds= {}, " +
                "states= {}, " +
                "catIds= {}, " +
                "rangeStart= {} ," +
                "rangeEnd= {} ," +
                "from= {}, " +
                "size= {}",
                userIds, states, catIds, rangeStart, rangeEnd, from, size);

        return eventServiceAdmin.getAll(userIds, states, catIds, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(@PathVariable(value = "eventId") Long eventId,
                                   @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("ApiAdmin. Запрос редактирования данных события с id= {} администратором", eventId);
        return eventServiceAdmin.patchEvent(eventId, updateEventAdminRequest);
    }
}
