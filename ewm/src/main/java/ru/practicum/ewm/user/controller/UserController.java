package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.user.Create;
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
        log.debug("Total number of events: {}. Method: GET/findEvents in UserController", eventService.getEventsOfUser(userId, from, size).size());

        return eventService.getEventsOfUser(userId, from, size);
    }

    @PatchMapping("/events")
    @Validated({Update.class})
    public EventFullDto updateEvent(@PathVariable("id") long userId,
                                    @RequestBody @Valid NewEventDto newEventDto) {
        EventFullDto eventDtoUpdated = eventService.updateEvent(userId, newEventDto);
        log.debug("Event updated, NewEventDto = {}. Method: PATCH/updateEvent in UserController", newEventDto);

        return eventDtoUpdated;
    }

    @PostMapping("/events")
    @Validated({Create.class})
    public EventFullDto createEvent(@PathVariable("id") long userId, @RequestBody @Valid NewEventDto newEventDto) {
        EventFullDto eventDtoSaved = eventService.saveEvent(userId, newEventDto);
        log.debug("Create new event NewEventDto = {}, Method: POST/createEvent in UserController", newEventDto);

        return eventDtoSaved;
    }

    @GetMapping("/events/{eventId}")
    public EventFullDto getEvent(@PathVariable("id") long userId, @PathVariable("eventId") long eventId) {
        log.info("Get user's event by userId = {}, eventId = {}. " +
                "Method: GET/getEvent in UserController", userId, eventId);
        EventFullDto eventFullDto = eventService.getUserEvent(userId, eventId);

        return eventFullDto;
    }

    @PatchMapping("/events/{eventId}")
    public EventFullDto deleteEvent(@PathVariable("id") long userId, @PathVariable("eventId") long eventId) {
        EventFullDto eventFullDto = eventService.deleteEventById(userId, eventId);
        log.debug("User's event with userid = {} , eventId = {} - canceled. " +
                "Method: PATCH/deleteEvent in UserController", userId, eventId);

        return eventFullDto;
    }

}
