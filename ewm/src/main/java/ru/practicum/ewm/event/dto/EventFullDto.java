package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.event.EventState;
import ru.practicum.ewm.event.Location;
import ru.practicum.ewm.user.dto.UserShortDto;

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
    private CategoryDto category;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Location location;
    private boolean paid;//Нужно ли оплачивать участие
    private int participantLimit;//Ограничение на количество участников.
    private boolean requestModeration; // Нужна ли пре-модерация заявок на участие
    private int confirmedRequests;//Количество одобренных заявок на участие в данном событии
    private UserShortDto initiator;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    private EventState state;//Список состояний жизненного цикла события
    private long views;//Количество просмотрев события
}
