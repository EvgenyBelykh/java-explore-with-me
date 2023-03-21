package ru.practicum.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.common.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Boolean existsByNameContaining(String name);
}