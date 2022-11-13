package ru.practicum.ewm.event;

public interface StatisticsClient {
    void saveEndpointHit(String uri, String ip);
}
