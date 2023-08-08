package com.example.Library.repository;

import com.example.Library.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    Optional<Reader> findByUsername(String username);
    Optional<Reader> findByEmail(String email);
}
