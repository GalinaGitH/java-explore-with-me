package ru.practicum.ewm.request;

import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    /**
     * PRIVATE:
     * получение информации о запросах на участие в собитии текущего пользователя
     */
    List<ParticipationRequestDto> getRequestsOfEvent(long userId, long eventId);

    /**
     * подтверждение чужой заявки  на участие в собитии текущего пользователя
     */
    ParticipationRequestDto confirmParticipationRequest(long userId, long eventId, long requestId);

    /**
     * отклонение чужой заявки  на участие в событии текущего пользователя
     */
    ParticipationRequestDto rejectParticipationRequest(long userId, long eventId, long requestId);

    /**
     * получение информации о заявках текущего пользователя на участие в чужих событиях
     */
    List<ParticipationRequestDto> getRequests(long userId);

    /**
     * добавление запроса текущего пользователя на участие в событии
     */
    ParticipationRequestDto addRequests(long userId, long eventId);

    /**
     * отмена своего запроса на участие в событии
     */
    ParticipationRequestDto cancelParticipationRequest(long userId, long requestId);

}
