package ru.practicum.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.common.models.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Boolean existsByNameContaining(String name);
    Category findByName(String name);
}
