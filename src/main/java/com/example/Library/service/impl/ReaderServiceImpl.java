package com.example.Library.service.impl;

import com.example.Library.exception.NotFoundException;
import com.example.Library.dto.ReaderDto;
import com.example.Library.model.Reader;
import com.example.Library.repository.ReaderRepository;
import com.example.Library.service.interfaces.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {
    private final ReaderRepository readerRepository;

    @Override
    public void create(Reader newReader) {
        List<Reader> readers = readerRepository.findAll();
        boolean isExist;
        isExist = readers.stream().anyMatch(x -> x.equals(newReader));
        if (!isExist) {
            readerRepository.save(newReader);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Reader read(Long id) {
        return readerRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public ReaderDto readDto(Long id) {
        Reader reader = readerRepository.findById(id).orElseThrow(NotFoundException::new);
        return ReaderDto.builder()
                .id(id)
                .fio(reader.getFio())
                .email(reader.getEmail())
                .username(reader.getUsername())
                .isArchived(reader.getIsArchived())
                .build();
    }

    @Override
    public List<ReaderDto> readAll() {
        List<Reader> readers = readerRepository.findAll();
        List<ReaderDto> readerDtoList = new ArrayList<>();
        for (Reader reader : readers) {
            readerDtoList.add(ReaderDto.builder()
                    .id(reader.getId())
                    .fio(reader.getFio())
                    .email(reader.getEmail())
                    .username(reader.getUsername())
                    .isArchived(reader.getIsArchived())
                    .build()
            );
        }
        return readerDtoList;
    }

    @Override
    public void update(Reader updateReader) {

        Reader reader = readerRepository.findById(updateReader.getId()).orElseThrow(NotFoundException::new);

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
        Reader reader = readerRepository.findById(id).orElseThrow(NotFoundException::new);
        reader.setIsArchived(true);
        readerRepository.save(reader);
    }


    public String getEmail(Long id) {
        return readerRepository.findById(id).orElseThrow(NotFoundException::new).getEmail();
    }

    @Override
    public Reader readByUsername(String username) {
        return readerRepository.findByUsername(username).orElseThrow(NotFoundException::new);
    }


}
