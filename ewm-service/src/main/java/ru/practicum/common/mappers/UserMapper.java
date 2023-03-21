package ru.practicum.common.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.common.dto.NewUserRequest;
import ru.practicum.common.dto.UserDto;
import ru.practicum.common.dto.UserShortDto;
import ru.practicum.common.models.User;

@Component
public class UserMapper {
    public User toUser(NewUserRequest newUserRequest) {
        return new User(newUserRequest.getEmail(),
                newUserRequest.getName());
    }

    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(),
                user.getEmail(),
                user.getName());
    }

    public UserShortDto toUserShortDto(User user) {
        return new UserShortDto(user.getId(),
                user.getName());
    }
}
