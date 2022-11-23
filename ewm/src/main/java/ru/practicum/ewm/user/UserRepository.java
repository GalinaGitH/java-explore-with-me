package ru.practicum.ewm.user;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllUsersByIdIn(List<Long> ids, Pageable pageable);

    User findByEmail(String email);
}
