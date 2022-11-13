package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsClientImpl implements StatisticsClient {
    @Override
    public void saveEndpointHit(String uri, String ip) {

    }
}
