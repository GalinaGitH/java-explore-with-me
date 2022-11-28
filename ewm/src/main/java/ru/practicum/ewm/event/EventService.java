package ru.practicum.ewm.event;

import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    /**
     * PRIVATE:
     * добавление нового события
     */
    EventFullDto saveEvent(long userId, NewEventDto newEventDto);

    /**
     * изменение события, добавленного пользователем
     */
    EventFullDto updateEvent(long userId, NewEventDto newEventDto);

    /**
     * получение событий добавленных текущим пользователем
     */
    List<EventShortDto> getEventsOfUser(long userId, int from, int size);

    /**
     * получение полной информации о событии, добавленном текущим пользователем
     */
    EventFullDto getUserEvent(long userId, long eventId);

    /**
     * отмена события, добавленного текущим пользователем
     */
    EventFullDto deleteEventById(long userId, long eventId);


    /**
     * PUBLIC:
     * получение всех событий с возможностью фильтрации
     */

    List<EventShortDto> getAllEvents(String text, List<Long> categories, Boolean paid,
                                     LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                     Boolean onlyAvailable, String sort, int from, int size);

    /**
     * PUBLIC:
     * получение подробной информации об опубликованном событии по его id
     */
    EventFullDto getEventById(long eventId);


    /**
     * ADMIN:
     * поиск событий
     */

    List<EventFullDto> getEvents(List<Long> users, List<EventState> states,
                                  List<Long> categories, LocalDateTime rangeStart,
                                  LocalDateTime rangeEnd, int from, int size);

    /**
     * ADMIN:
     * редактирование события
     */
    EventFullDto updateEventByAdmin(long eventId, NewEventDto newEventDto);

    /**
     * ADMIN:
     * публикация события
     */
    EventFullDto publishAnEvent(long eventId);

    /**
     * ADMIN:
     * отклонение события
     */
    EventFullDto rejectEvent(long eventId);
}
