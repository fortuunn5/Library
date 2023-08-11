package com.example.Library.service.impl;

import com.example.Library.model.Reader;
import com.example.Library.repository.ReaderRepository;
import com.example.Library.service.interfaces.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {
    private final ReaderRepository readerRepository;

    @Override
    public void create(Reader newReader) {
        if(readerRepository.findByEmail(newReader.getEmail()).isEmpty()) {
            readerRepository.save(newReader);
        }
    }

    @Override
    public Optional<Reader> read(Long id) {
        return readerRepository.findById(id);
    }

    @Override
    public List<Reader> readAll() {
        return readerRepository.findAll();
    }

    @Override
    public void update(Reader updateReader) {

        Reader reader = readerRepository.findById(updateReader.getId()).orElseThrow();

        if(updateReader.getFio() != null && !updateReader.getFio().isBlank()) {
            reader.setFio(updateReader.getFio());
        }
        if(updateReader.getEmail() != null && !updateReader.getEmail().isBlank()) {
            reader.setEmail(updateReader.getEmail());
        }
        if(updateReader.getUsername() != null && !updateReader.getUsername().isBlank()) {
            reader.setUsername(updateReader.getUsername());
        }
        if(updateReader.getPassword() != null && !updateReader.getPassword().isBlank()) {
            reader.setPassword(updateReader.getPassword());
        }
        if(updateReader.getRole() != null && !updateReader.getRole().isBlank()) {
            reader.setRole(updateReader.getRole());
        }
        if(updateReader.getIsArchived() != null) {
            reader.setIsArchived(updateReader.getIsArchived());
        }
        readerRepository.save(reader);
    }

    @Override
    public void delete(Long id) {
        Reader reader = readerRepository.findById(id).orElseThrow();
        reader.setIsArchived(true);
        readerRepository.save(reader);
    }


    public String getEmail(Long id) {
        return readerRepository.findById(id).orElseThrow().getEmail();
    }

    @Override
    public Reader readByUsername(String username) {
        return readerRepository.findByUsername(username).orElseThrow();
    }


}
