package ru.practicum.stats;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.model.EndpointHit;

@Component
@AllArgsConstructor
public class StatsMapper {

    public EndpointHitDto toDto(EndpointHit endpointHit) {
        return new EndpointHitDto(
                endpointHit.getApp(),
                endpointHit.getUri(),
                endpointHit.getIp(),
                endpointHit.getTimestamp()
        );
    }
}
