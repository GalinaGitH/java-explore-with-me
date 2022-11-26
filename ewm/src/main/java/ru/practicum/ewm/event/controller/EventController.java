package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.EventsSort;
import ru.practicum.ewm.event.StatisticsClient;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/events")
@Slf4j
public class EventController {

    private final EventService eventService;
    private final StatisticsClient statisticsClient;

    @SneakyThrows
    @GetMapping
    public List<EventShortDto> getAllEvents(@RequestParam(name = "text", required = false) String text,
                                            @RequestParam(name = "categories", required = false) List<Long> categories,
                                            @RequestParam(name = "paid", required = false) Boolean paid,
                                            @RequestParam(name = "rangeStart", required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                            @RequestParam(name = "rangeEnd", required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                            @RequestParam(name = "onlyAvailable", required = false) Boolean onlyAvailable,
                                            @RequestParam(name = "sort", defaultValue = "EVENT_DATE") EventsSort sort,
                                            @RequestParam(name = "from", defaultValue = "0") int from,
                                            @RequestParam(name = "size", defaultValue = "10") int size,
                                            HttpServletRequest request) {
        statisticsClient.saveEndpointHit(request);
        log.info("Get List of events with the possibility of filtering. " +
                "Method: GET/getAllEvents in EventController");

        return eventService.getAllEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort.name(), from, size);
    }


    @SneakyThrows
    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable("id") long eventId, HttpServletRequest request) {
        log.info("Get full information about a published event  by eventId = {} ." +
                "Method: GET/getEventById in EventController", eventId);
        EventFullDto eventFullDto = eventService.getEventById(eventId);
        statisticsClient.saveEndpointHit(request);

        return eventFullDto;
    }
}
