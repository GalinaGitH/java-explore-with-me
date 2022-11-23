package ru.practicum.ewm.request;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.user.User;

import java.time.LocalDateTime;

@Component
public class ParticipationRequestMapper {

    public ParticipationRequestDto toDto(ParticipationRequest participationRequest) {
        return new ParticipationRequestDto(
                participationRequest.getId(),
                participationRequest.getEvent().getId(),
                participationRequest.getRequester().getId(),
                participationRequest.getStatus(),
                participationRequest.getCreatedOn()
        );
    }

    public ParticipationRequest toRequest(Event event, User user) {
        return new ParticipationRequest(
                null,
                event,
                user,
                ParticipationStatus.PENDING,
                LocalDateTime.now()
        );
    }
}
