package ru.practicum.ewm.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findEventsByInitiator_Id(long userId, Pageable pageable);

    Event findByIdAndState(long eventId, EventState state);

    List<Event> findEventsByIdIn(Set<Long> events);

    @Query("SELECT e FROM Event e ORDER BY e.id ASC")
    List<Event> findAllEvents(Pageable pageable);
}
