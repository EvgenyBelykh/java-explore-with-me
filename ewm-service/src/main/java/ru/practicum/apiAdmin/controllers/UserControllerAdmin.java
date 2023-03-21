package ru.practicum.apiAdmin.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiAdmin.services.UserServiceAdmin;
import ru.practicum.common.dto.NewUserRequest;
import ru.practicum.common.dto.UserDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Slf4j
public class UserControllerAdmin {
    private final UserServiceAdmin userServiceAdmin;

    @PostMapping
    public UserDto add(@Valid @RequestBody NewUserRequest newUserRequest) {
        log.info("Запрос добавления пользователя с email {}", newUserRequest.getEmail());
        return userServiceAdmin.add(newUserRequest);
    }

    @GetMapping
    public List<UserDto> get(@RequestParam(value = "from", defaultValue = "0") Integer from,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             @RequestParam(value = "ids", required = false) List<Long> ids) {
        log.info("Запрос получения всех пользователей");
        return userServiceAdmin.get(from, size, ids);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") Long idUser) {
        log.info("Запрос удаления пользователя с id {}", idUser);
        userServiceAdmin.remove(idUser);
    }
}
