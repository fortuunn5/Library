package com.example.Library.controller;

import com.example.Library.dto.BookDto;
import com.example.Library.dto.LogbookDto;
import com.example.Library.dto.ReaderDto;
import com.example.Library.model.Book;
import com.example.Library.model.Logbook;
import com.example.Library.model.LogbookKey;
import com.example.Library.model.Reader;
import com.example.Library.service.interfaces.BookService;
import com.example.Library.service.interfaces.LogbookService;
import com.example.Library.service.interfaces.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/logbooks")
@RequiredArgsConstructor
public class LogbookController {
    private final LogbookService logbookService;
    private final ReaderService readerService;
    private final BookService bookService;

    @PostMapping
    //TODO: fix
    public ResponseEntity<?> createLogbook(@RequestBody LogbookKey newLogbookKey) {
        Reader reader = readerService.read(newLogbookKey.getReaderId());
        Book book = bookService.read(newLogbookKey.getBookId());

        if (reader.getIsArchived() || book.getIsArchived()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Logbook logbook = Logbook.builder()
                .id(newLogbookKey)
                .reader(reader)
                .book(book)
                .build();
        logbookService.create(newLogbookKey);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<LogbookDto> readLogbook(@RequestBody LogbookKey id) {
            return ResponseEntity.ok(logbookService.readDto(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<LogbookDto>> readLogbooks() {
        return ResponseEntity.ok(logbookService.readAllDto());
    }

    @GetMapping("/user")
    public ResponseEntity<List<LogbookDto>> readReadersLogbooks() {
        return ResponseEntity.ok(logbookService.readReadersLogbooks());
    }

    @PutMapping
    public ResponseEntity<?> updateLogbook(@RequestBody Logbook newLogbook) {
        logbookService.update(newLogbook);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteLogbook(@RequestBody LogbookKey id) {
        logbookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
