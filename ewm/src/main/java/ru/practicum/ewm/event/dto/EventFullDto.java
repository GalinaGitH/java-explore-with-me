package ru.practicum.ewm.event.dto;

import lombok.*;
import ru.practicum.ewm.category.EventCategory;
import ru.practicum.ewm.event.EventState;
import ru.practicum.ewm.event.Location;
import ru.practicum.ewm.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class EventFullDto {

    private Long id;
    private String title;
    private String description;
    private String annotation;
    private EventCategory category;
    private LocalDateTime eventDate;
    private Location location;
    private boolean paid;//Нужно ли оплачивать участие
    private int participantLimit;//Ограничение на количество участников.
    private boolean requestModeration; // Нужна ли пре-модерация заявок на участие
    private int confirmedRequests;//Количество одобренных заявок на участие в данном событии
    private User initiator;
    private LocalDateTime createdOn;
    private LocalDateTime publishedOn;
    private EventState state;//Список состояний жизненного цикла события
    private long views;//Количество просмотрев события
}
