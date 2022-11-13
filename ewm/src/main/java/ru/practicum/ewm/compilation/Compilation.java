package ru.practicum.ewm.compilation;

import lombok.*;
import ru.practicum.ewm.event.Event;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",
            nullable = false)
    @Size(min = 3, max = 120)
    private String title;
    private boolean pinned; //Закреплена ли подборка на главной странице сайта

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "events_compilations",
            joinColumns = {@JoinColumn(name = "compilation_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")})
    private final List<Event> events = new ArrayList<>();

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }
}
