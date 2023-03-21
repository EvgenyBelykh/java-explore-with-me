package ru.practicum.common.models;

import lombok.*;
import ru.practicum.common.enums.State;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "events", schema = "public")

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "annotation", nullable = false)
    private String annotation;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private LocationModel locationModel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "initiator_id", nullable = false)
    @ToString.Exclude
    private User initiator;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "confirmed_requests")
    private Long confirmedRequests;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Column(name = "paid", nullable = false)
    private Boolean paid;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @Column(name = "views")
    private Long views;

    public Event(String title,
                 String annotation,
                 String description,
                 Category category,
                 LocationModel locationModel,
                 User initiator,
                 LocalDateTime eventDate,
                 LocalDateTime createdOn,
                 Boolean requestModeration,
                 Boolean paid,
                 Integer participantLimit,
                 State state,
                 Long views) {
        this.title = title;
        this.annotation = annotation;
        this.description = description;
        this.category = category;
        this.locationModel = locationModel;
        this.initiator = initiator;
        this.eventDate = eventDate;
        this.createdOn = createdOn;
        this.requestModeration = requestModeration;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.state = state;
        this.views = views;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(title, event.title) && Objects.equals(annotation, event.annotation) && Objects.equals(description, event.description) && Objects.equals(category, event.category) && Objects.equals(locationModel, event.locationModel) && Objects.equals(initiator, event.initiator) && Objects.equals(eventDate, event.eventDate) && Objects.equals(createdOn, event.createdOn) && Objects.equals(publishedOn, event.publishedOn) && Objects.equals(confirmedRequests, event.confirmedRequests) && Objects.equals(requestModeration, event.requestModeration) && Objects.equals(paid, event.paid) && Objects.equals(participantLimit, event.participantLimit) && state == event.state && Objects.equals(views, event.views);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, annotation, description, category, locationModel, initiator, eventDate, createdOn, publishedOn, confirmedRequests, requestModeration, paid, participantLimit, state, views);
    }
}
