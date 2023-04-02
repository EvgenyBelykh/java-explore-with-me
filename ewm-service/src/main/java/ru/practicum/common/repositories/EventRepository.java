package ru.practicum.common.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.common.models.Event;

public interface EventRepository extends JpaRepository<Event, Long>, CustomEventRepository {
    Page<Event> findAllByInitiatorId(Long idUser, Pageable pageable);

    Boolean existsByCategoryId(Long idCat);
}
