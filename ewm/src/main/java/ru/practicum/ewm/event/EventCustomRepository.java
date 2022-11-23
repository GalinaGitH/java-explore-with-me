package ru.practicum.ewm.event;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.List;

public interface EventCustomRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    static Specification<Event> hasText(String text) {
        return (event, cq, cb) -> {
            if (text == null) {
                return cb.isTrue(cb.literal(true));
            } else {
                String search = text.toUpperCase();
                return cb.or(cb.like(cb.upper(event.get("annotation")), "%" + search + "%"),
                        cb.like(cb.upper(event.get("description")), "%" + search + "%"));
            }
        };
    }

    static Specification<Event> hasCategories(List<Long> categories) {
        return (event, cq, cb) -> {
            if (categories == null && categories.size() == 0) {
                return cb.isTrue(cb.literal(true));
            } else {
                CriteriaBuilder.In<Long> categoriesIds = cb.in(event.get("category"));
                for (long catId : categories) {
                    categoriesIds.value(catId);
                }
                return categoriesIds;
            }
        };
    }

    static Specification<Event> hasPaid(Boolean paid) {
        return (event, cq, cb) -> {
            if (paid == null && !paid) {
                return cb.isTrue(cb.literal(true));
            } else {
                return cb.equal(event.get("paid"), paid);
            }
        };
    }

    static Specification<Event> hasRangeStart(LocalDateTime rangeStart) {
        return (event, cq, cb) -> {
            if (rangeStart == null) {
                return cb.isTrue(cb.literal(true));
            } else {
                return cb.greaterThan(event.get("eventDate"), rangeStart);
            }
        };
    }

    static Specification<Event> hasRangeEnd(LocalDateTime rangeEnd) {
        return (event, cq, cb) -> {
            if (rangeEnd == null) {
                return cb.isTrue(cb.literal(true));
            } else {
                return cb.lessThan(event.get("eventDate"), rangeEnd);
            }
        };
    }

    static Specification<Event> hasAvailable(Boolean onlyAvailable) {
        return (event, cq, cb) -> {
            if (onlyAvailable != null && onlyAvailable) {
                return cb.or(cb.le(event.get("confirmedRequests"), event.get("participantLimit")),
                        cb.le(event.get("participantLimit"), 0));
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    static Specification<Event> hasUsers(List<Long> users) {
        return (event, cq, cb) -> {
            if (users == null) {
                return cb.isTrue(cb.literal(true));
            } else {
                CriteriaBuilder.In<Long> usersIds = cb.in(event.get("initiator"));
                for (long userId : users) {
                    usersIds.value(userId);
                }
                return usersIds;
            }
        };
    }

    static Specification<Event> hasStates(List<EventState> states) {
        return (event, cq, cb) -> {
            if (states == null) {
                return cb.isTrue(cb.literal(true));
            } else {
                CriteriaBuilder.In<EventState> states2 = cb.in(event.get("state"));
                for (EventState state : states) {
                    states2.value(state);
                }
                return states2;
            }
        };
    }

}
