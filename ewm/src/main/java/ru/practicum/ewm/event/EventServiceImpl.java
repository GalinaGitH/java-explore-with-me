package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    @Override
    public EventFullDto saveEvent(NewEventDto newEventDto) {
        return null;
    }

    @Override
    public EventFullDto updateEvent(long userId, NewEventDto newEventDto) {
        return null;
    }

    @Override
    public List<EventShortDto> getEventsOfUser(long userId, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto getUserEvent(long userId, long eventId) {
        return null;
    }

    @Override
    public void deleteEventById(long userId, long eventId) {

    }

    @Override
    public List<EventShortDto> getAllEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto getEventById(long eventId) {
        return null;
    }

    @Override
    public List<EventShortDto> getEvents(List<Long> users, List<EventState> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto updateEventByAdmin(long eventId, NewEventDto newEventDto) {
        return null;
    }

    @Override
    public EventFullDto publishAnEvent(long eventId) {
        return null;
    }

    @Override
    public EventFullDto rejectEvent(long eventId) {
        return null;
    }
}
