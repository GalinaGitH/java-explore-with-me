package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.CompilationService;
import ru.practicum.ewm.compilation.dto.CompilationDto;

import javax.validation.Valid;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/compilations")
@Slf4j
public class AdminCompilationController {
    private final CompilationService compilationService;

    @PostMapping
    public CompilationDto addCompilation(@RequestBody @Valid CompilationDto compilationDto) {
        log.info("Create new compilation by admin");
        CompilationDto savedCompilationDto = compilationService.addCompilation(compilationDto);
        return savedCompilationDto;
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable("compId") long compId) {
        log.info("Delete compilation by admin, compId={}", compId);
        compilationService.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable("compId") long compId,
                                           @PathVariable("eventId") long eventId) {
        log.info("Delete event from compilation by admin, compId={} , eventId={}", compId, eventId);
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable("compId") long compId,
                                      @PathVariable("eventId") long eventId) {
        log.info("Add event to compilation");
        compilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unPinCompilation(@PathVariable("compId") long compId) {
        log.info("Unpin compilation on the main page by admin, compId={}", compId);
        compilationService.changePin(compId,false);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable("compId") long compId) {
        log.info("Pin compilation on the main page by admin, compId={}", compId);
        compilationService.changePin(compId,true);
    }

}