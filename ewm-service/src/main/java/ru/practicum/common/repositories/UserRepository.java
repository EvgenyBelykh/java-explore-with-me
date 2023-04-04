package ru.practicum.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.common.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmailContaining(String email);

}
