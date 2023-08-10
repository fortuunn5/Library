package com.example.Library.service.interfaces;

import com.example.Library.dto.ReaderDto;
import com.example.Library.model.Reader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReaderService {
    void create(Reader newReader);
    Optional<Reader> read(Long id);
    List<Reader> readAll();
    void update(Reader updateReader);
    void delete(Long id);

    Reader readByUsername(String username);
}
