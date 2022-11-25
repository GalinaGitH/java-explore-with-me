package ru.practicum.ewm.user;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllUsersByIdIn(List<Long> ids, Pageable pageable);

    Optional<User> findByEmail(String email);
}
