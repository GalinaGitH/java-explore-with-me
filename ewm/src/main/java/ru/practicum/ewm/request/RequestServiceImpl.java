package ru.practicum.ewm.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.event.EventState;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.exception.CanNotBeCanceledException;
import ru.practicum.ewm.request.exception.NonComplianceWithTheRulesOfParticipationException;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.UserRepository;
import ru.practicum.ewm.user.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

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
        return requests.stream().map(requestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmParticipationRequest(long userId, long eventId, long requestId) {
        ParticipationRequest request = requestRepository.findById(requestId).orElseThrow(NotFoundException::new);
        int numberOfApplications = request.getEvent().getConfirmedRequests();
        int limit = request.getEvent().getParticipantLimit();
        if (limit != 0 && numberOfApplications == limit) {
            throw new NonComplianceWithTheRulesOfParticipationException("limit of applications for participation is exhausted");
        }
        request.setStatus(ParticipationStatus.CONFIRMED);
        updateConfirmedRequests(eventId, numberOfApplications + 1);
        ParticipationRequest savedRequest = requestRepository.save(request);
        return requestMapper.toDto(savedRequest);
    }

    @Override
    @Transactional
    public ParticipationRequestDto rejectParticipationRequest(long userId, long eventId, long requestId) {
        ParticipationRequest request = requestRepository.findById(requestId).orElseThrow(NotFoundException::new);
        request.setStatus(ParticipationStatus.REJECTED);
        ParticipationRequest savedRequest = requestRepository.save(request);
        return requestMapper.toDto(savedRequest);
    }

    @Override
    public List<ParticipationRequestDto> getRequests(long userId) {
        List<ParticipationRequest> requests = requestRepository.findAllByRequester_Id(userId);
        return requests.stream().map(requestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto addRequest(long userId, long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);

        if (event.getInitiator().equals(user)) {
            throw new NonComplianceWithTheRulesOfParticipationException("initiator doesn't need to apply for participation");
        }
        if (event.getState() != EventState.PUBLISHED) {
            throw new NonComplianceWithTheRulesOfParticipationException("you can only participate in a published event");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new NonComplianceWithTheRulesOfParticipationException("limit of applications for participation is exhausted");
        }
        ParticipationRequest request = requestMapper.toRequest(event, user);

        if (!event.isRequestModeration()) {
            request.setStatus(ParticipationStatus.CONFIRMED);
        }
        ParticipationRequest savedRequest = requestRepository.save(request);
        return requestMapper.toDto(savedRequest);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelParticipationRequest(long userId, long requestId) {
        ParticipationRequest request = requestRepository.findById(requestId).orElseThrow(NotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        if (request.getStatus() != ParticipationStatus.PENDING) {
            throw new CanNotBeCanceledException("for cancellation status must be Pending");
        }
        request.setStatus(ParticipationStatus.CANCELED);
        requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    private void updateConfirmedRequests(long eventId, int count) {
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        event.setConfirmedRequests(count);
        eventRepository.save(event);
    }
}
