package ru.practicum.apiAdmin.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.dto.NewUserRequest;
import ru.practicum.common.exceptions.ExistEmailUserException;
import ru.practicum.common.exceptions.NotFindUserException;
import ru.practicum.common.exceptions.NotFindUsersException;
import ru.practicum.common.mappers.UserMapper;
import ru.practicum.common.dto.UserDto;
import ru.practicum.common.models.User;
import ru.practicum.common.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceAdminImpl implements UserServiceAdmin {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto add(NewUserRequest newUserRequest) {
        if (isEmailAlreadyInUse(newUserRequest.getEmail())) {
            throw new ExistEmailUserException("Пользователь с email=" + newUserRequest.getEmail() + " уже есть в базе");
        }

        User user = userRepository.save(userMapper.toUser(newUserRequest));
        log.info("Сохранение пользователя в БД с id: {}", user.getId());
        return userMapper.toUserDto(user);
    }

    @Override
    public void remove(Long idUser) {
        userRepository.findById(idUser).orElseThrow(() -> new NotFindUserException(idUser));
        userRepository.deleteById(idUser);
        log.info("Удален пользователь с id={}", idUser);
    }

    @Override
    public List<UserDto> get(Integer from, Integer size, List<Long> ids) {
        if(ids != null) {
            Iterable<User> users = userRepository.findAllById(ids);
            List<UserDto> userDtoList = new ArrayList<>();
            for (User user : users) {
                userDtoList.add(userMapper.toUserDto(user));
            }
            log.info("Возвращен список пользователей по запросу списка идентификаторов: {} ", ids);
            return userDtoList;
        } else {
            Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "id"));
            Page<User> userPage = userRepository.findAll(pageable);
            if(userPage.getContent().isEmpty()) {
                throw new NotFindUsersException("Нет данных для вывода или база пуста.");
            }
            log.info("Возвращен список пользователей с пагинацией  от пользователя с id= {} размером {}", from, size);
            return userPage.getContent().stream().map(userMapper::toUserDto).collect(Collectors.toList());
        }
    }

    @Override
    public boolean isEmailAlreadyInUse(String email) {
        return userRepository.existsByEmailContaining(email);
    }
}
