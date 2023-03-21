package ru.practicum.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.common.models.User;

import java.util.List;
@Repository
public interface  UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmailContaining(String email);

}
