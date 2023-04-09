package ru.practicum.common.repositories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.common.enums.SortEvents;
import ru.practicum.common.enums.State;
import ru.practicum.common.models.Event;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class CustomEventRepositoryImpl implements CustomEventRepository {

    private final EntityManager entityManager;

    @Override
    public List<Event> privateSearch(
            List<Long> userIds, List<State> states, List<Long> catIds,
            LocalDateTime rangeStart, LocalDateTime rangeEnd,
            Integer from, Integer size) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);

        Root<Event> rootEvent = criteriaQuery.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();

        if (userIds != null && userIds.size() > 0) {
            In<Long> inClause = criteriaBuilder.in(rootEvent.get("initiator"));
            for (Long userId : userIds) {
                inClause.value(userId);
            }
            predicates.add(inClause);
        }

        if (states != null && states.size() > 0) {
            In<State> inClause = criteriaBuilder.in(rootEvent.get("state"));
            for (State state : states) {
                inClause.value(state);
            }
            predicates.add(inClause);
        }

        addCatIds(catIds, criteriaBuilder, rootEvent, predicates);
        addTime(rangeStart, rangeEnd, criteriaBuilder, rootEvent, predicates);
        return getEventList(criteriaQuery, predicates, from, size);
    }

    @Override
    public List<Event> publicSearch(
            String text, List<Long> categories, Boolean paid,
            LocalDateTime rangeStart, LocalDateTime rangeEnd,
            Boolean onlyAvailable, SortEvents sort, Integer from, Integer size) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);

        Root<Event> rootEvent = criteriaQuery.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(rootEvent.get("state"), State.PUBLISHED));

        if (text != null) {
            String search = text.toLowerCase();
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(rootEvent.get("annotation")), "%" + search + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(rootEvent.get("description")), "%" + search + "%")
            ));
        }

        addCatIds(categories, criteriaBuilder, rootEvent, predicates);

        if (paid != null) {
            predicates.add(criteriaBuilder.equal(rootEvent.get("paid"), paid));
        }

        if (onlyAvailable != null && onlyAvailable) {
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.le(rootEvent.get("confirmedRequests"), rootEvent.get("participantLimit")),
                    criteriaBuilder.le(rootEvent.get("participantLimit"), 0)
            ));
        }

        if (sort.equals(SortEvents.EVENT_DATE)) {
            criteriaQuery.orderBy(criteriaBuilder.desc(rootEvent.get("eventDate")));
        } else if (sort.equals(SortEvents.VIEWS)) {
            criteriaQuery.orderBy(criteriaBuilder.desc(rootEvent.get("views")));
        }

        addTime(rangeStart, rangeEnd, criteriaBuilder, rootEvent, predicates);

        return getEventList(criteriaQuery, predicates, from, size);
    }

    private void addCatIds(List<Long> catIds,
                           CriteriaBuilder criteriaBuilder,
                           Root<Event> rootEvent,
                           List<Predicate> predicates) {
        if (catIds != null && catIds.size() > 0) {
            In<Long> inClause = criteriaBuilder.in(rootEvent.get("category"));
            for (Long catId : catIds) {
                inClause.value(catId);
            }
            predicates.add(inClause);
        }
    }

    private void addTime(LocalDateTime rangeStart,
                         LocalDateTime rangeEnd,
                         CriteriaBuilder criteriaBuilder,
                         Root<Event> rootEvent,
                         List<Predicate> predicates) {
        if (rangeStart != null) {
            predicates.add(criteriaBuilder.greaterThan(rootEvent.get("eventDate"), rangeStart));
        }

        if (rangeEnd != null) {
            predicates.add(criteriaBuilder.lessThan(rootEvent.get("eventDate"), rangeEnd));
        }

        if (rangeEnd == null && rangeStart == null) {
            predicates.add(criteriaBuilder.greaterThan(rootEvent.get("eventDate"), LocalDateTime.now()));
        }
    }

    private List<Event> getEventList(CriteriaQuery<Event> criteriaQuery,
                                     List<Predicate> predicates,
                                     Integer from,
                                     Integer size) {
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(from).setMaxResults(size).getResultList();
    }
}
