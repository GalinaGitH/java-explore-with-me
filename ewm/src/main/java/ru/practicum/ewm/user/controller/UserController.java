package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.user.Update;

import javax.validation.Valid;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users/{id}")
@Slf4j
public class UserController {
    private final EventService eventService;

    @GetMapping("/events")
    public List<EventShortDto> findEvents(@PathVariable("id") long userId,
                                          @RequestParam(name = "from", defaultValue = "0") int from,
                                          @RequestParam(name = "size", defaultValue = "10") int size) {
        log.debug("Total number of events: {}", eventService.getEventsOfUser(userId, from, size).size());
        return eventService.getEventsOfUser(userId, from, size);
    }

    @PatchMapping("/events")
    public EventFullDto updateEvent(@PathVariable("id") long userId,
                                    @RequestBody @Valid @Validated({Update.class}) NewEventDto newEventDto) {
        EventFullDto eventDtoUpdated = eventService.updateEvent(userId, newEventDto);
        log.debug("Event updated");
        return eventDtoUpdated;
    }

    @PostMapping("/events")
    public EventFullDto createEvent(@PathVariable("id") long userId, @RequestBody @Valid NewEventDto newEventDto) {
        EventFullDto eventDtoSaved = eventService.saveEvent(newEventDto);
        log.debug("Number of added events: {}", 1);
        return eventDtoSaved;
    }

    @GetMapping("/events/{eventId}")
    public EventFullDto getEvent(@PathVariable("id") long userId, @PathVariable("eventId") long eventId) {
        log.info("Get user's event by userId={}, eventId={}", userId, eventId);
        EventFullDto eventFullDto = eventService.getUserEvent(userId, eventId);
        return eventFullDto;
    }

    @PatchMapping("/events/{eventId}")
    public void deleteEvent(@PathVariable("id") long userId, @PathVariable("eventId") long eventId) {
        eventService.deleteEventById(userId, eventId);
        log.debug("User's event with userid= {} , eventId={} - canceled", userId, eventId);
    }

}
