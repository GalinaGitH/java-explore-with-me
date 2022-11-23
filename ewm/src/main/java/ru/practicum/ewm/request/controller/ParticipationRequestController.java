package ru.practicum.ewm.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.RequestService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users/{id}")
@Slf4j
public class ParticipationRequestController {
    private final RequestService requestService;

    @GetMapping("/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsOfEvent(@PathVariable("id") long userId, @PathVariable("eventId") long eventId) {
        log.info("Get user's requests to participate by userId={},  in eventId={}", userId, eventId);
        return requestService.getRequestsOfEvent(userId, eventId);
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmParticipationRequest(@PathVariable("id") long userId,
                                                               @PathVariable("eventId") long eventId,
                                                               @PathVariable("reqId") long requestId) {
        log.info("Confirmation of someone else's application for participation in event (eventId={}) of the userId={}", eventId, userId);
        return requestService.confirmParticipationRequest(userId, eventId, requestId);
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectParticipationRequest(@PathVariable("id") long userId,
                                                              @PathVariable("eventId") long eventId,
                                                              @PathVariable("reqId") long requestId) {
        log.info("Reject of someone else's application for participation in event (eventId={}) of the userId={}", eventId, userId);
        return requestService.rejectParticipationRequest(userId, eventId, requestId);
    }

    @GetMapping("/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable("id") long userId) {
        log.info("Get user's requests to participate in different events by userId={} ", userId);
        return requestService.getRequests(userId);
    }

    @PostMapping("/requests")
    public ParticipationRequestDto addRequests(@PathVariable("id") long userId,
                                               @RequestParam("eventId") long eventId) {
        log.info("Add user's requests to participate in  event(eventId={}) by userId={} ", eventId, userId);
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelParticipationRequest(@PathVariable("id") long userId,
                                                              @PathVariable("requestId") long requestId) {
        log.info("Cancel your event request  (requestId={}) , userId={}", requestId, userId);
        return requestService.cancelParticipationRequest(userId, requestId);
    }

}
