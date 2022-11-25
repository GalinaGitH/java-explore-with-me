package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.EventState;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.user.Update;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/events")
@Slf4j
public class AdminEventController {
    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> findAllEvents(@RequestParam(name = "users", required = false) List<Long> users,
                                            @RequestParam(name = "states", required = false) List<EventState> states,
                                            @RequestParam(name = "categories", required = false) List<Long> categories,
                                            @RequestParam(name = "rangeStart", required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                            @RequestParam(name = "rangeEnd", required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                            @RequestParam(name = "from", defaultValue = "0") int from,
                                            @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("Get List of events with the possibility of filtering. " +
                "Method: GET/findAllEvents in AdminEventController");
        return eventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEventByAdmin(@PathVariable("eventId") long eventId,
                                           @RequestBody @Valid @Validated({Update.class}) NewEventDto newEventDto) {
        log.info("Update event by admin, eventId = {}", eventId);
        EventFullDto eventFullDto = eventService.updateEventByAdmin(eventId, newEventDto);
        return eventFullDto;
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishAnEvent(@PathVariable("eventId") long eventId) {
        log.info("Publish an event by admin, eventId = {}", eventId);
        EventFullDto eventFullDto = eventService.publishAnEvent(eventId);
        return eventFullDto;
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable("eventId") long eventId) {
        log.info("Reject event by admin, eventId = {}", eventId);
        EventFullDto eventFullDto = eventService.rejectEvent(eventId);
        return eventFullDto;
    }
}
