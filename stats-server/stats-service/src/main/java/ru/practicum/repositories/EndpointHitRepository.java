package ru.practicum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.EndpointHit;

public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long>, CustomEndpointHitRepository {
}
