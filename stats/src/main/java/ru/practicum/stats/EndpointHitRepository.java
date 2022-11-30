package ru.practicum.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT new ru.practicum.stats.model.ViewStats(e.app, e.uri, COUNT (e.ip)) from EndpointHit e " +
            "WHERE e.timestamp> ?1 AND e.timestamp< ?2 AND ((?3) is null or e.uri in ?3) GROUP BY e.uri,e.app ORDER BY COUNT (e.uri) DESC, e.uri")
    List<ViewStats> findAll(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.stats.model.ViewStats(e.app, e.uri, COUNT (DISTINCT e.ip)) from EndpointHit e" +
            " WHERE e.timestamp> ?1 AND e.timestamp< ?2 AND ((?3) is null or e.uri in ?3) GROUP BY e.uri,e.app ORDER BY COUNT (DISTINCT e.ip) DESC, e.uri")
    List<ViewStats> findAllUnique(LocalDateTime startCod, LocalDateTime endCod, List<String> uris);
}
