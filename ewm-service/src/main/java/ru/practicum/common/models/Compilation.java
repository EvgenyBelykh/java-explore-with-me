package ru.practicum.common.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "compilations", schema = "public")

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "pinned", nullable = false)
    private Boolean pinned;

    @ManyToMany
    @JoinTable(
            name = "compilation_events",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> events;

    public Compilation(String title, Boolean pinned, List<Event> events) {
        this.title = title;
        this.pinned = pinned;
        this.events = events;
    }
}
