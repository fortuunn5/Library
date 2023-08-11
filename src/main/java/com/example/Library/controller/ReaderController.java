package com.example.Library.controller;

import com.example.Library.dto.ReaderDto;
import com.example.Library.model.Reader;
import com.example.Library.service.interfaces.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/readers")
@RequiredArgsConstructor
public class ReaderController {
    private final ReaderService readerService;

    @PostMapping
    public ResponseEntity<?> createReader(@RequestBody Reader newReader) {
        List<Reader> readers = readerService.readAll();
        for (Reader reader : readers) {
            if (reader.equals(newReader))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        readerService.create(newReader);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReaderDto> readReader(@PathVariable(name="id") Long id) {
        if(readerService.read(id).isPresent()) {
            Reader reader = readerService.read(id).orElseThrow();
            ReaderDto readerDto = ReaderDto.builder()
                    .id(reader.getId())
                    .fio(reader.getFio())
                    .email(reader.getEmail())
                    .username(reader.getUsername())
                    .isArchived(reader.getIsArchived())
                    .build();
            return ResponseEntity.ok(readerDto);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<ReaderDto>> readReaders() {
        if(!readerService.readAll().isEmpty()) {
            List<Reader> readers = readerService.readAll();
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
            return ResponseEntity.ok(readerDtoList);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> updateReader(@RequestBody Reader upReader) {
        if (readerService.read(upReader.getId()).isPresent()) {
            readerService.update(upReader);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReader(@PathVariable(name="id") Long id) {
        if(readerService.read(id).isPresent()) {
            readerService.delete(readerService.read(id).orElseThrow().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
