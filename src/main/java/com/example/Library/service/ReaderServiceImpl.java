package com.example.Library.service;

import com.example.Library.dto.ReaderDto;
import com.example.Library.model.Reader;
import com.example.Library.repository.ReaderRepository;
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

        List<Reader> readers = readerRepository.findAll();
        boolean isExist=false;
        for(int i=0; i<readers.size(); i++) {
            if(readers.get(i).getEmail().equals(newReader.getEmail())) {
                isExist=true;
            }
        }
        if(!isExist) {
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
        reader.setRole(updateReader.getRole());
        readerRepository.save(reader);
    }

    @Override
    public void delete(Long id) {
        readerRepository.deleteById(id);
    }


    public String getEmail(Long id) {
        return readerRepository.findById(id).orElseThrow().getEmail();
    }


}
