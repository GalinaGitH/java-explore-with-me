package ru.practicum.ewm.compilation;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.user.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        int page = from / size;
        if (pinned != null) {
            return compilationRepository.findAllByPinned(pinned, PageRequest.of(page, size)).stream()
                    .map(compilationMapper::toDto)
                    .collect(Collectors.toList());
        }
        return compilationRepository.findAll(PageRequest.of(page, size)).stream()
                .map(compilationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilation(long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Not found compilation with id = " + compId));
        return compilationMapper.toDto(compilation);
    }

    @Override
    @Transactional
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = new Compilation(null, newCompilationDto.getTitle(), newCompilationDto.isPinned());
        List<Event> events = eventRepository.findEventsByIdIn(newCompilationDto.getEvents());
        for (Event e : events) {
            compilation.addEvent(e);
        }
        Compilation savedCompilation = compilationRepository.save(compilation);
        return compilationMapper.toDto(savedCompilation);
    }

    @Override
    public void deleteCompilation(long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Not found compilation with id = " + compId));
        compilationRepository.delete(compilation);
    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Not found compilation with id = " + compId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Not found event with id = " + eventId));
        compilation.removeEvent(event);
        compilationRepository.save(compilation);
    }

    @Override
    public void addEventToCompilation(long compId, long eventId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Not found compilation with id = " + compId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Not found event with id = " + eventId));
        compilation.addEvent(event);
        compilationRepository.save(compilation);
    }

    @Override
    public void changePin(long compId, boolean isPinned) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Not found compilation with id = " + compId));
        compilation.setPinned(isPinned);
        compilationRepository.save(compilation);
    }
}
