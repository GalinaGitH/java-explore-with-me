package ru.practicum.ewm.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.event.Event;

import java.util.List;
import java.util.Optional;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findAllByEvent_Id(long eventId);

    List<ParticipationRequest> findAllByRequester_Id(long userId);

    @Query("SELECT p FROM ParticipationRequest p WHERE p.event.id IN ?2 AND p.requester.id<?1 ")
    Optional<Object> getRequestOfUserInEvent(long userId, Event event);

    @Query("SELECT p FROM ParticipationRequest p WHERE p.event.id IN ?1 AND p.status='PENDING'")
    List<ParticipationRequest> findAllPendingParticipationRequestsOfEvent(long eventId);
}
