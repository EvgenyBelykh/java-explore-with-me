package ru.practicum.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.common.models.LocationModel;

public interface LocationRepository extends JpaRepository<LocationModel, Long> {
}
