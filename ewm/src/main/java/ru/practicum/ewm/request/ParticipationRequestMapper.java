package ru.practicum.ewm.request;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

@Component
public class ParticipationRequestMapper {

    public ParticipationRequestDto toDto(ParticipationRequest participationRequest) {
        return new ParticipationRequestDto(
                participationRequest.getId(),
                participationRequest.getEvent(),
                participationRequest.getRequester(),
                participationRequest.getStatus(),
                participationRequest.getCreatedOn()
        );
    }

    public ParticipationRequest toRequest(ParticipationRequestDto participationRequestDto) {
        return new ParticipationRequest(
                participationRequestDto.getId(),
                participationRequestDto.getEvent(),
                participationRequestDto.getRequester(),
                participationRequestDto.getStatus(),
                participationRequestDto.getCreatedOn()
        );
    }
}
