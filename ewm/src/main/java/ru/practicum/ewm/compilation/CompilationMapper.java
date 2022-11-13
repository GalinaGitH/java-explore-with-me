package ru.practicum.ewm.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.event.EventMapper;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper {
    private final EventMapper eventMapper;

    public CompilationDto toDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getTitle(),
                compilation.isPinned(),
                compilation.getEvents().stream().map(eventMapper::toShortDto)
                        .collect(Collectors.toList())
        );
    }
}
