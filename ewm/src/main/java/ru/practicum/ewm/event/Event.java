package ru.practicum.ewm.event;

import lombok.*;
import ru.practicum.ewm.category.EventCategory;
import ru.practicum.ewm.user.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",
            nullable = false)
    @Size(min = 3, max = 120)
    private String title;

    @Column(name = "description",
            nullable = false)
    @Size(min = 20, max = 7000)
    private String description;

    @Column(name = "annotation",
            nullable = false)
    @Size(min = 20, max = 2000)
    private String annotation;

    @JoinColumn(name = "category_id",
            nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private EventCategory category;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "lat", column = @Column(name = "location_lat")),
            @AttributeOverride(name = "lon", column = @Column(name = "location_lon"))})
    private Location location;
    private boolean paid;//Нужно ли оплачивать участие

    @Column(name = "participant_limit")
    private int participantLimit;//Ограничение на количество участников.

    @Column(name = "request_moderation")
    private boolean requestModeration; // Нужна ли пре-модерация заявок на участие

    @Column(name = "confirmed_requests")
    private int confirmedRequests;//Количество одобренных заявок на участие в данном событии

    @JoinColumn(name = "initiator_id",
            nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User initiator;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "published_on", nullable = false)
    private LocalDateTime publishedOn;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventState state;//Список состояний жизненного цикла события
    private long views;//Количество просмотрев события

    public void addViews() {
        views++;
    }
}
