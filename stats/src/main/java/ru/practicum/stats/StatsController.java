package ru.practicum.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.model.ViewStats;

import javax.validation.Valid;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@Slf4j
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    public EndpointHitDto hit(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        log.info("Add info about request");
        return statsService.addHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam(name = "start") long start,
                                    @RequestParam(name = "end") long end,
                                    @RequestParam(name = "uris", required = false) List<String> uris,
                                    @RequestParam(name = "unique", defaultValue = "false") boolean unique) {
        log.info("Getting statistics on visits");
        return statsService.getStats(start, end, uris, unique);
    }
}
