package ru.practicum.ewm.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.UserRepository;
import ru.practicum.ewm.user.exception.NotFoundException;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.request.ParticipationStatus.CONFIRMED;
import static ru.practicum.ewm.request.ParticipationStatus.REJECTED;
import static ru.practicum.ewm.request.ParticipationStatus.CANCELED;
import static ru.practicum.ewm.request.ParticipationStatus.PENDING;
import static ru.practicum.ewm.event.EventState.PUBLISHED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ParticipationRequestRepository requestRepository;
    private final ParticipationRequestMapper requestMapper;

    @Override
    public List<ParticipationRequestDto> getRequestsOfEvent(long userId, long eventId) {
        List<ParticipationRequest> requests = requestRepository.findAllByEvent_Id(eventId);

        return requests.stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmParticipationRequest(long userId, long eventId, long requestId) {
        ParticipationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Not found request with id = " + requestId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Not found event with id = " + eventId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id = " + userId));
        if (!event.getInitiator().equals(user)) {
            throw new ValidationException("Initiator can confirm applications " +
                    "for participation, but user with id =" + userId + "not an initiator of event eventId = " + eventId);
        }
        int numberOfApplications = request.getEvent().getConfirmedRequests();
        int limit = request.getEvent().getParticipantLimit();
        if (limit != 0 && numberOfApplications == limit) {
            throw new ValidationException("limit of applications for participation" +
                    " is exhausted");
        }
        if (numberOfApplications == limit - 1) {
            rejectAllPendingParticipationRequestsOfEvent(eventId);
        }
        request.setStatus(CONFIRMED);
        eventRepository.setEventInfoById(eventId, numberOfApplications + 1);
        ParticipationRequest savedRequest = requestRepository.save(request);

        return requestMapper.toDto(savedRequest);
    }

    @Override
    @Transactional
    public ParticipationRequestDto rejectParticipationRequest(long userId, long eventId, long requestId) {
        ParticipationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Not found request with id = " + requestId));
        request.setStatus(REJECTED);
        ParticipationRequest savedRequest = requestRepository.save(request);

        return requestMapper.toDto(savedRequest);
    }

    @Override
    public List<ParticipationRequestDto> getRequests(long userId) {
        List<ParticipationRequest> requests = requestRepository.findAllByRequester_Id(userId);

        return requests.stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto addRequest(long userId, long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Not found event with id = " + eventId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id = " + userId));

        if (event.getInitiator().equals(user)) {
            throw new ValidationException("initiator doesn't need to apply" +
                    " for participation");
        }
        if (event.getState() != PUBLISHED) {
            throw new ValidationException("you can only participate in a published event");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new ValidationException("limit of applications " +
                    "for participation is exhausted");
        }
        ParticipationRequest request = requestMapper.toRequest(event, user);

        if (!event.isRequestModeration()) {
            request.setStatus(CONFIRMED);
        }
        ParticipationRequest savedRequest = requestRepository.save(request);

        return requestMapper.toDto(savedRequest);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelParticipationRequest(long userId, long requestId) {
        ParticipationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Not found request with id = " + requestId));
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("You can cancel only your participation request, " +
                        "but we didn't find user with id = " + userId));
        if (request.getStatus() != PENDING) {
            throw new ValidationException("For cancellation status must be Pending");
        }
        request.setStatus(CANCELED);
        requestRepository.save(request);

        return requestMapper.toDto(request);
    }


    private void rejectAllPendingParticipationRequestsOfEvent(long eventId) {
        List<ParticipationRequest> pendingRequests =
                requestRepository.findAllPendingParticipationRequestsOfEvent(eventId);
        pendingRequests.forEach(r -> r.setStatus(REJECTED));
        requestRepository.saveAll(pendingRequests);
    }
}
