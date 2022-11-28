package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.CategoryRepository;
import ru.practicum.ewm.category.EventCategory;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.exception.InAppropriateStatusException;
import ru.practicum.ewm.event.exception.NotAnInitiatorOfEventException;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.UserRepository;
import ru.practicum.ewm.user.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;
import static ru.practicum.ewm.event.EventCustomRepository.*;
import static ru.practicum.ewm.event.EventState.CANCELED;
import static ru.practicum.ewm.event.EventState.PENDING;
import static ru.practicum.ewm.event.EventState.PUBLISHED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventCustomRepository customRepository;
    private final EventMapper eventMapper;

    @Override
    @Transactional
    public EventFullDto saveEvent(long userId, NewEventDto newEventDto) {
        User initiator = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found initiator(user) with id = " + userId));
        EventCategory categoryInStorage = categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new NotFoundException("Not found category with id = " + newEventDto.getCategory()));
        Event event = eventMapper.toModel(initiator, categoryInStorage, newEventDto);
        Event savedEvent = eventRepository.save(event);

        return eventMapper.toDto(savedEvent);
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(long userId, NewEventDto newEventDto) {
        Event event = eventRepository.findById(newEventDto.getEventId())
                .orElseThrow(() -> new NotFoundException("Not found event with id = " + newEventDto.getEventId()));
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found requester(user) with id = " + userId));
        if (!event.getInitiator().equals(requester)) {
            throw new NotAnInitiatorOfEventException("only initiator or admin can update event");
        }
        if (event.getState() == PUBLISHED) {
            throw new InAppropriateStatusException("event is published already");
        }
        makeChangesToEntity(event, newEventDto);
        Event savedEvent = eventRepository.save(event);

        return eventMapper.toDto(savedEvent);
    }

    @Override
    public List<EventShortDto> getEventsOfUser(long userId, int from, int size) {
        int page = from / size;
        List<Event> events = eventRepository.findEventsByInitiator_Id(userId, PageRequest.of(page, size));

        return events.stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getUserEvent(long userId, long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Not found event with id = " + eventId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id = " + userId));
        if (!user.equals(event.getInitiator())) {
            throw new NotAnInitiatorOfEventException("it's not an user event");
        }

        return eventMapper.toDto(event);
    }

    @Override
    @Transactional
    public EventFullDto deleteEventById(long userId, long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Not found event with id = " + eventId));
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found requester(user) with id = " + userId));
        if (!event.getInitiator().equals(requester)) {
            throw new NotAnInitiatorOfEventException("only initiator or admin can cancel event");
        }
        if (event.getState() != PENDING) {
            throw new InAppropriateStatusException("not in PENDING state");
        }
        event.setState(CANCELED);
        eventRepository.save(event);
        Event savedEvent = eventRepository.save(event);

        return eventMapper.toDto(savedEvent);
    }

    @Override
    public List<EventShortDto> getAllEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, int from, int size) {

        switch (sort) {
            case "EVENT_DATE":
                List<Event> events = customRepository.findAll(where(hasText(text)).and(hasCategories(categories))
                        .and(hasPaid(paid)).and(hasRangeStart(rangeStart)).and(hasRangeEnd(rangeEnd))
                        .and(hasAvailable(onlyAvailable)));

                return events.stream()
                        .sorted(Comparator.comparing(Event::getEventDate))
                        .map(eventMapper::toShortDto).collect(Collectors.toList());

            case "VIEWS":
                events = customRepository.findAll(where(hasText(text)).and(hasCategories(categories)).and(hasPaid(paid))
                        .and(hasRangeStart(rangeStart)).and(hasRangeEnd(rangeEnd)).and(hasAvailable(onlyAvailable)));

                return events.stream()
                        .sorted(Comparator.comparing(Event::getViews))
                        .map(eventMapper::toShortDto).collect(Collectors.toList());
            default:
                events = customRepository.findAll(where(hasText(text)).and(hasCategories(categories)).and(hasPaid(paid))
                        .and(hasRangeStart(rangeStart)).and(hasRangeEnd(rangeEnd)).and(hasAvailable(onlyAvailable)));

                return events.stream()
                        .sorted(Comparator.comparing(Event::getId))
                        .map(eventMapper::toShortDto).collect(Collectors.toList());
        }
    }

    @Override
    public EventFullDto getEventById(long eventId) {
        Event event = eventRepository.findByIdAndState(eventId, PUBLISHED)
                .orElseThrow(() -> new NotFoundException("Not found published event with id = " + eventId));
        event.addViews();
        eventRepository.save(event);

        return eventMapper.toDto(event);
    }

    @Override
    public List<EventFullDto> getEvents(List<Long> users, List<EventState> states, List<Long> categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        int page = from / size;
        if (users != null || states != null || categories != null || rangeStart != null || rangeEnd != null) {
            List<Event> events = customRepository.findAll(where(hasUsers(users)).and(hasStates(states))
                    .and(hasCategories(categories)).and(hasRangeStart(rangeStart)).and(hasRangeEnd(rangeEnd)));

            return events.stream()
                    .sorted(Comparator.comparing(Event::getId))
                    .map(eventMapper::toDto).collect(Collectors.toList());
        } else {
            List<Event> events = eventRepository.findAllEvents(PageRequest.of(page, size));

            return events.stream().map(eventMapper::toDto).collect(Collectors.toList());
        }
    }

    @Override
    @Transactional
    public EventFullDto updateEventByAdmin(long eventId, NewEventDto newEventDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Not found event with id = " + eventId));
        makeChangesToEntity(event, newEventDto);
        if (newEventDto.getLocation() != null) {
            event.setLocation(newEventDto.getLocation());
        }
        Event savedEvent = eventRepository.save(event);

        return eventMapper.toDto(savedEvent);
    }

    @Override
    @Transactional
    public EventFullDto publishAnEvent(long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Not found event with id = " + eventId));
        if (event.getState() != PENDING) {
            throw new InAppropriateStatusException("Event must be in PENDING state for publishing");
        }
        event.setState(PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        eventRepository.save(event);

        return eventMapper.toDto(event);
    }

    @Override
    @Transactional
    public EventFullDto rejectEvent(long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Not found event with id = " + eventId));
        if (event.getState() == PUBLISHED) {
            throw new InAppropriateStatusException("event is published already");
        }
        event.setState(CANCELED);
        eventRepository.save(event);

        return eventMapper.toDto(event);
    }

    private void makeChangesToEntity(Event event, NewEventDto newEventDto) {
        if (newEventDto.getTitle() != null) {
            event.setTitle(newEventDto.getTitle());
        }
        if (newEventDto.getAnnotation() != null) {
            event.setAnnotation(newEventDto.getAnnotation());
        }
        if (newEventDto.getDescription() != null) {
            event.setDescription(newEventDto.getDescription());
        }
        if (newEventDto.getCategory() != null) {
            EventCategory category = categoryRepository.findById(newEventDto.getCategory())
                    .orElseThrow(() -> new NotFoundException("Not found category with id = " + newEventDto.getCategory()));
            event.setCategory(category);
        }
        if (newEventDto.getEventDate() != null) {
            event.setEventDate(newEventDto.getEventDate());
        }
        if (newEventDto.getPaid() != null) {
            event.setPaid(newEventDto.getPaid());
        }
        if (newEventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(newEventDto.getParticipantLimit());
        }
    }
}
