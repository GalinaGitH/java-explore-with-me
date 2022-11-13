package ru.practicum.ewm.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    @Override
    public List<ParticipationRequestDto> getRequestsOfEvent(long userId, long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto confirmParticipationRequest(long userId, long eventId, long requestId) {
        return null;
    }

    @Override
    public ParticipationRequestDto rejectParticipationRequest(long userId, long eventId, long requestId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getRequests(long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto addRequests(long userId, long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancelParticipationRequest(long userId, long requestId) {
        return null;
    }
}
