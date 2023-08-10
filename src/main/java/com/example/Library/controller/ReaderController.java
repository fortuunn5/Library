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
        for(int i=0; i<readers.size(); i++) {
            if(readers.get(i).equals(newReader))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        readerService.create(newReader);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReaderDto> readReader(@PathVariable(name="id") Long id) {
        if(readerService.read(id).isPresent()) {
            Reader reader = readerService.read(id).orElseThrow();
            ReaderDto readerDto = new ReaderDto(reader.getId(), reader.getFio(), reader.getEmail(), reader.getUsername(), reader.getIsArchived());
            return ResponseEntity.ok(readerDto);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<ReaderDto>> readReaders() {
        if(!readerService.readAll().isEmpty()) {
            List<Reader> readers = readerService.readAll();
            List<ReaderDto> readerDtos = new ArrayList<>();
            for(int i=0; i<readers.size(); i++) {
                Long curId = readers.get(i).getId();
                String curFio = readers.get(i).getFio();
                String curEmail = readers.get(i).getEmail();
                String curUsername = readers.get(i).getUsername();
                Boolean curIsArchived = readers.get(i).getIsArchived();
                readerDtos.add(new ReaderDto(curId, curFio, curEmail, curUsername, curIsArchived));
            }
            return ResponseEntity.ok(readerDtos);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReader(@PathVariable(name="id") Long id, @RequestBody Reader upReader) {
        if(id.equals(upReader.getId())) {
            if (readerService.read(id).isPresent()) {
                readerService.update(upReader);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
