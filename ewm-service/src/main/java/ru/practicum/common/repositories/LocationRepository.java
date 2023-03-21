package ru.practicum.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.common.models.LocationModel;

@Repository
public interface LocationRepository extends JpaRepository<LocationModel, Long> {
}
