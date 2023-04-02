package ru.practicum.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.common.models.ParticipationRequest;

import java.util.List;
import java.util.Optional;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> findAllByRequesterId(Long userId);

    @Query("SELECT pr FROM ParticipationRequest pr WHERE pr.event.id=:eventId AND pr.requester.id=:idUser")
    Optional<ParticipationRequest> findByRequesterIdAndEventId(
            @Param("eventId") Long eventId,
            @Param("idUser") Long idUser);

    List<ParticipationRequest> findAllByEventId(Long idEvent);
}
