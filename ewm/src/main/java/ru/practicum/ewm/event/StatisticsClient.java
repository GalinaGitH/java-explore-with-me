package ru.practicum.ewm.event;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.http.HttpServletRequest;

public interface StatisticsClient {
    void saveEndpointHit(HttpServletRequest request) throws JsonProcessingException;
}
