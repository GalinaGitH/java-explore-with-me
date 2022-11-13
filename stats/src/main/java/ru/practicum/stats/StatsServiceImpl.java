package ru.practicum.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.model.ViewStats;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    @Override
    public EndpointHitDto addHit(EndpointHitDto endpointHitDto) {
        return null;
    }

    @Override
    public List<ViewStats> getStats(long start, long end, List<String> uris, boolean unique) {
        return null;
    }
}
