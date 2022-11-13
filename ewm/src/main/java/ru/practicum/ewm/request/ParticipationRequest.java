package ru.practicum.ewm.request;

import lombok.*;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "requests")
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "event_id",
            nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @JoinColumn(name = "requester_id",
            nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User requester;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParticipationStatus status;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
}
