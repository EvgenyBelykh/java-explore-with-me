package ru.practicum.repositories.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;
import ru.practicum.repositories.CustomEndpointHitRepository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class CustomEndpointHitRepositoryImpl implements CustomEndpointHitRepository {
    private final EntityManager entityManager;

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ViewStats> criteriaQuery = criteriaBuilder.createQuery(ViewStats.class);
        Root<EndpointHit> endpointHitRoot = criteriaQuery.from(EndpointHit.class);

        List<Predicate> predicates = new ArrayList<>();

        criteriaQuery.select(criteriaBuilder.construct(ViewStats.class, endpointHitRoot.get("uri"),
                endpointHitRoot.get("app"),
                unique ? criteriaBuilder.countDistinct(endpointHitRoot.get("ip")) :
                        criteriaBuilder.count(endpointHitRoot.get("ip"))));

        criteriaQuery.groupBy(endpointHitRoot.get("app"),
                endpointHitRoot.get("uri"),
                endpointHitRoot.get("ip"));

        criteriaQuery.orderBy(criteriaBuilder.desc(unique ? criteriaBuilder.countDistinct(endpointHitRoot.get("ip")) :
                criteriaBuilder.count(endpointHitRoot.get("ip"))));

        predicates.add(criteriaBuilder.between(endpointHitRoot.get("timestamp"), start, end));
        if (!uris.isEmpty()) {
            CriteriaBuilder.In<String> in = criteriaBuilder.in(endpointHitRoot.get("uri"));
            for (String uri : uris) {
                in.value(uri);
            }
            predicates.add(in);
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
