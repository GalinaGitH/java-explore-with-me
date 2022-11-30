package ru.practicum.stats;

import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.model.ViewStats;

import java.util.List;

public interface StatsService {

    /**
     * Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.
     * Название сервиса, uri и ip пользователя указаны в теле запроса.
     */
    EndpointHitDto addHit(EndpointHitDto endpointHitDto);

    /**
     * Получение статистики по посещениям
     */
    List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique);
}
