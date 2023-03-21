package ru.practicum.apiAdmin.services;

import ru.practicum.common.dto.NewUserRequest;
import ru.practicum.common.dto.UserDto;

import java.util.List;

public interface UserServiceAdmin {
    UserDto add(NewUserRequest newUserRequest);

    void remove(Long idUser);

    List<UserDto> get(Integer from, Integer size, List<Long> ids);

    boolean isEmailAlreadyInUse(String email);
}
