package com.example.Library.controller;

import com.example.Library.dto.ReaderDto;
import com.example.Library.model.Reader;
import com.example.Library.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ReaderController {
    private final ReaderService readerService;

    @PostMapping("/readers")
    public ResponseEntity<?> createReader(@RequestBody Reader newReader) {
        List<Reader> readers = readerService.readAll();
        for(int i=0; i<readers.size(); i++) {
            if(readers.get(i).getFio().equals(newReader.getFio())
                    && readers.get(i).getEmail().equals(newReader.getEmail())
                    && readers.get(i).getUsername().equals(newReader.getUsername())
                    && readers.get(i).getPassword().equals(newReader.getPassword())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        readerService.create(newReader);
        return new ResponseEntity<>(HttpStatus.CREATED);
        //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/readers/{id}")
    public ResponseEntity<ReaderDto> readReader(@PathVariable(name="id") Long id) {
        if(readerService.read(id).isPresent()) {
            Reader reader = readerService.read(id).orElseThrow();
            ReaderDto readerDto = new ReaderDto(reader.getId(), reader.getFio(), reader.getEmail(), reader.getUsername(), reader.getIsArchived());
            return ResponseEntity.ok(readerDto);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/readers")
    public ResponseEntity<List<ReaderDto>> readReaders() {
        if(!readerService.readAll().isEmpty()) {
            List<Reader> readers = readerService.readAll();
            List<ReaderDto> readerDtos = new ArrayList<>();
            for(int i=0; i<readers.size(); i++) {
                readerDtos.add(new ReaderDto(readers.get(i).getId(), readers.get(i).getFio(), readers.get(i).getEmail(), readers.get(i).getUsername(), readers.get(i).getIsArchived()));
            }

            return ResponseEntity.ok(readerDtos);
            //return ResponseEntity.ok(readerService.readAll());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/readers/{id}")
    public ResponseEntity<?> updateReader(@PathVariable(name="id") Long id, @RequestBody Reader upReader) {
        if(id.equals(upReader.getUsername())) {
            if (readerService.read(id).isPresent()) {
                readerService.update(upReader);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/readers/{id}")
    public ResponseEntity<?> deleteReader(@PathVariable(name="id") Long id) {
        if(readerService.read(id).isPresent()) {
            readerService.delete(readerService.read(id).orElseThrow().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
