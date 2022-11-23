package ru.practicum.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT new ru.practicum.stats.model.ViewStats(e.app, e.uri, COUNT (e.ip)) from EndpointHit e WHERE e.timestamp> ?1 AND e.timestamp< ?2 GROUP BY e.app, e.uri")
    List<ViewStats> findAll(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.stats.model.ViewStats(e.app, e.uri, COUNT (DISTINCT e.ip)) from EndpointHit e WHERE e.timestamp> ?1 AND e.timestamp< ?2 GROUP BY e.app, e.uri")
    List<ViewStats> findAllUnique(LocalDateTime startCod, LocalDateTime endCod, List<String> uris, boolean unique);
}
