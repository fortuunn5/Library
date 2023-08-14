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
        readerService.create(newReader);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReaderDto> readReaderDto(@PathVariable(name="id") Long id) {
        return ResponseEntity.ok(readerService.readDto(id));
    }

    @GetMapping
    public ResponseEntity<List<ReaderDto>> readReaders() {

        return ResponseEntity.ok(readerService.readAll());
    }

    @PutMapping
    public ResponseEntity<?> updateReader(@RequestBody Reader upReader) {
        readerService.update(upReader);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReader(@PathVariable(name="id") Long id) {
        readerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
