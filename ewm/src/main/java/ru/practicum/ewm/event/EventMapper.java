package ru.practicum.ewm.event;


import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;

@Component
public class EventMapper {
    public EventShortDto toShortDto(Event event) {
        return new EventShortDto(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                event.getCategory(),
                event.getConfirmedRequests(),
                event.getEventDate(),
                event.getInitiator(),
                event.isPaid(),
                event.getViews()
        );
    }

    public Event toEvent(EventFullDto eventFullDto) {
        return new Event(
                eventFullDto.getId(),
                eventFullDto.getTitle(),
                eventFullDto.getDescription(),
                eventFullDto.getAnnotation(),
                eventFullDto.getCategory(),
                eventFullDto.getEventDate(),
                eventFullDto.getLocation(),
                eventFullDto.isPaid(),
                eventFullDto.getParticipantLimit(),
                eventFullDto.isRequestModeration(),
                eventFullDto.getConfirmedRequests(),
                eventFullDto.getInitiator(),
                eventFullDto.getCreatedOn(),
                eventFullDto.getPublishedOn(),
                eventFullDto.getState(),
                eventFullDto.getViews()
        );
    }

    public EventFullDto toDto(Event event) {
        return new EventFullDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getAnnotation(),
                event.getCategory(),
                event.getEventDate(),
                event.getLocation(),
                event.isPaid(),
                event.getParticipantLimit(),
                event.isRequestModeration(),
                event.getConfirmedRequests(),
                event.getInitiator(),
                event.getCreatedOn(),
                event.getPublishedOn(),
                event.getState(),
                event.getViews()
        );
    }
}
