package ru.practicum.ewm.event.dto;

import lombok.*;
import ru.practicum.ewm.category.EventCategory;
import ru.practicum.ewm.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class EventShortDto {
    private Long id;
    private String title;
    private String annotation;
    private EventCategory category;
    private int confirmedRequests;
    private LocalDateTime eventDate;
    private User initiator;
    private boolean paid;
    private long views;
}
