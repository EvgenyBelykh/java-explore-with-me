package ru.practicum.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.services.StatService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StatsController {
    private final StatService statService;

    @GetMapping("/test")
    public String test() {
        return "OK from controller!";
    }

    @PostMapping("/hit")
    public ResponseEntity<EndpointHitDto> hit(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        log.info("Запрос сохранения информации об отправке запроса пользователем с ip={} на адрес={} ",
                endpointHitDto.getIp(), endpointHitDto.getUri());

        return new ResponseEntity<>(statService.add(endpointHitDto), HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam(name = "start") String start,
                                   @RequestParam(name = "end") String end,
                                   @RequestParam(name = "uris", required = false) List<String> uris,
                                   @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("Запрос получения статистики по посещениям");

        return statService.getStat(start, end, uris, unique);
    }
}
