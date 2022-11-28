package ru.practicum.ewm.event;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.CategoryMapper;
import ru.practicum.ewm.category.EventCategory;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.UserMapper;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class EventMapper {
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    public EventShortDto toShortDto(Event event) {
        return new EventShortDto(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                categoryMapper.toDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getEventDate(),
                userMapper.toShortDto(event.getInitiator()),
                event.isPaid(),
                event.getViews()
        );
    }

    public EventFullDto toDto(Event event) {
        return new EventFullDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getAnnotation(),
                categoryMapper.toDto(event.getCategory()),
                event.getEventDate(),
                event.getLocation(),
                event.isPaid(),
                event.getParticipantLimit(),
                event.isRequestModeration(),
                event.getConfirmedRequests(),
                userMapper.toShortDto(event.getInitiator()),
                event.getCreatedOn(),
                event.getPublishedOn(),
                event.getState(),
                event.getViews()
        );
    }

    public Event toModel(User initiator, EventCategory eventCategory, NewEventDto newEventDto) {
        return new Event(
                newEventDto.getEventId(),
                newEventDto.getTitle(),
                newEventDto.getDescription(),
                newEventDto.getAnnotation(),
                eventCategory,
                newEventDto.getEventDate(),
                newEventDto.getLocation(),
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit(),
                newEventDto.getRequestModeration(),
                0,
                initiator,
                LocalDateTime.now(),
                LocalDateTime.now(),
                EventState.PENDING,
                0
        );
    }
}
