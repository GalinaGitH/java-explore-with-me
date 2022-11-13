package ru.practicum.ewm.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.dto.CompilationDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        return null;
    }

    @Override
    public CompilationDto getCompilation(long compId) {
        return null;
    }

    @Override
    public CompilationDto addCompilation(CompilationDto compilationDto) {
        return null;
    }

    @Override
    public void deleteCompilation(long compId) {

    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {

    }

    @Override
    public void addEventToCompilation(long compId, long eventId) {

    }

    @Override
    public void changePin(long compId, boolean isPinned) {

    }
}
