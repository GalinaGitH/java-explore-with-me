package ru.practicum.ewm.compilation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompilationRepository extends CrudRepository<Compilation, Long> {
    List<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);

    List<Compilation> findAll(Pageable pageable);
}
