package ru.practicum.common.models;

import lombok.*;
import ru.practicum.common.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ParticipationRequests", schema = "public")

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requester_id")
    @ToString.Exclude
    private User requester;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    @ToString.Exclude
    private Event event;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipationRequest request = (ParticipationRequest) o;
        return Objects.equals(id, request.id) && Objects.equals(created, request.created) && status == request.status && Objects.equals(requester, request.requester) && Objects.equals(event, request.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, status, requester, event);
    }
}
