package ru.practicum.ewm.compilation;

import ru.practicum.ewm.compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationService {

    /**
     * PUBLIC:
     * получение подборок событий
     */
    List<CompilationDto> getCompilations(Boolean pinned, int from, int size);

    /**
     * PUBLIC:
     * получение подбороки события по его id
     */
    CompilationDto getCompilation(long compId);

    /**
     * ADMIN:
     * добавление новой подборки
     */
    CompilationDto addCompilation(CompilationDto compilationDto);

    /**
     * ADMIN:
     * удаление подборки
     */
    void deleteCompilation(long compId);

    /**
     * ADMIN:
     * удаление события из подборки
     */
    void deleteEventFromCompilation(long compId, long eventId);

    /**
     * ADMIN:
     * добавление события в подборку
     */
    void addEventToCompilation(long compId, long eventId);

    /**
     * ADMIN:
     * открепить/закрепить подборку на главной странице
     */
    void changePin(long compId, boolean isPinned);

}
