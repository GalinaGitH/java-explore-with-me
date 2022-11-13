package ru.practicum.ewm.request.dto;

import lombok.*;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.request.ParticipationStatus;
import ru.practicum.ewm.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class ParticipationRequestDto {

    private Long id;
    private Event event;
    private User requester;
    private ParticipationStatus status;
    private LocalDateTime createdOn;
}
