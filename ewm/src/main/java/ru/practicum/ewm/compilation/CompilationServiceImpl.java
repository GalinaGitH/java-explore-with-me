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
            List<Compilation> allCompilations = compilationRepository.findAllByPinned(pinned, PageRequest.of(page, size));
            return allCompilations.stream().map(compilationMapper::toDto).collect(Collectors.toList());
        }
        List<Compilation> allCompilations = compilationRepository.findAll(PageRequest.of(page, size));
        return allCompilations.stream().map(compilationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilation(long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(NotFoundException::new);
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
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(NotFoundException::new);
        compilationRepository.delete(compilation);
    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(NotFoundException::new);
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        compilation.removeEvent(event);
        compilationRepository.save(compilation);
    }

    @Override
    public void addEventToCompilation(long compId, long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(NotFoundException::new);
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        compilation.addEvent(event);
        compilationRepository.save(compilation);
    }

    @Override
    public void changePin(long compId, boolean isPinned) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(NotFoundException::new);
        compilation.setPinned(isPinned);
        compilationRepository.save(compilation);
    }
}
