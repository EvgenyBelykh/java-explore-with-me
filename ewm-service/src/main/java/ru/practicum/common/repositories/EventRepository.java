package ru.practicum.common.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.common.enums.State;
import ru.practicum.common.models.Event;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, CustomEventRepository {
    Page<Event> findAllByInitiatorId(Long idUser, Pageable pageable);
    Boolean existsByCategoryId(Long idCat);
}
