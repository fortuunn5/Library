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
    public ResponseEntity<?> createLogbook(@RequestBody LogbookKey newLogbookKey) {
        Reader reader = readerService.read(newLogbookKey.getReaderId()).orElseThrow();
        Book book = bookService.read(newLogbookKey.getBookId()).orElseThrow();

        if (reader.getIsArchived() || book.getIsArchived()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Logbook logbook = Logbook.builder()
                .id(newLogbookKey)
                .reader(reader)
                .book(book)
                .build();
        logbookService.create(logbook);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<LogbookDto> readLogbook(@RequestBody LogbookKey id) {
        if (logbookService.read(id).isPresent()) {
            Logbook logbook = logbookService.read(id).orElseThrow();
            ReaderDto readerDto = ReaderDto.builder()
                    .id(logbook.getReader().getId())
                    .fio(logbook.getReader().getFio())
                    .email(logbook.getReader().getEmail())
                    .username(logbook.getReader().getUsername())
                    .isArchived(logbook.getReader().getIsArchived())
                    .build();
            BookDto bookDto = BookDto.builder()
                    .id(logbook.getBook().getId())
                    .name(logbook.getBook().getName())
                    .year(logbook.getBook().getYear())
                    .isArchived(logbook.getBook().getIsArchived())
                    .build();
            LogbookDto logbookDto = LogbookDto.builder()
                    .id(logbook.getId())
                    .reader(readerDto)
                    .book(bookDto)
                    .issueDate(logbook.getIssueDate())
                    .deliveryDate(logbook.getDeliveryDate())
                    .isArchived(logbook.getIsArchived())
                    .build();
            return ResponseEntity.ok(logbookDto);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LogbookDto>> readLogbooks() {
        if (!logbookService.readAll().isEmpty()) {
            List<Logbook> logbooks = logbookService.readAll();
            List<LogbookDto> logbookDtoList = new ArrayList<>();

            for (Logbook logbook : logbooks) {
                ReaderDto readerDto = ReaderDto.builder()
                        .id(logbook.getReader().getId())
                        .fio(logbook.getReader().getFio())
                        .email(logbook.getReader().getEmail())
                        .username(logbook.getReader().getUsername())
                        .isArchived(logbook.getReader().getIsArchived())
                        .build();

                BookDto bookDto = BookDto.builder()
                        .id(logbook.getBook().getId())
                        .name(logbook.getBook().getName())
                        .author(logbook.getBook().getAuthor())
                        .year(logbook.getBook().getYear())
                        .isArchived(logbook.getBook().getIsArchived())
                        .build();

                logbookDtoList.add(LogbookDto.builder()
                        .id(logbook.getId())
                        .reader(readerDto)
                        .book(bookDto)
                        .issueDate(logbook.getIssueDate())
                        .deliveryDate(logbook.getDeliveryDate())
                        .isArchived(logbook.getIsArchived())
                        .build()
                );
            }
            return ResponseEntity.ok(logbookDtoList);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user")
    public ResponseEntity<List<LogbookDto>> readReadersLogbooks() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Reader reader = readerService.readByUsername(userDetails.getUsername());

        ReaderDto readerDto = ReaderDto.builder()
                .id(reader.getId())
                .fio(reader.getFio())
                .email(reader.getEmail())
                .username(reader.getUsername())
                .isArchived(reader.getIsArchived())
                .build();

        List<Logbook> logbooks = logbookService.readReadersLogbooks(reader);
        List<LogbookDto> logbookDtoList = new ArrayList<>();

        for (Logbook logbook : logbooks) {

            BookDto bookDto = BookDto.builder()
                    .id(logbook.getBook().getId())
                    .name(logbook.getBook().getName())
                    .author(logbook.getBook().getAuthor())
                    .year(logbook.getBook().getYear())
                    .isArchived(logbook.getBook().getIsArchived())
                    .build();

            logbookDtoList.add(LogbookDto.builder()
                    .id(logbook.getId())
                    .reader(readerDto)
                    .book(bookDto)
                    .issueDate(logbook.getIssueDate())
                    .deliveryDate(logbook.getDeliveryDate())
                    .isArchived(logbook.getIsArchived())
                    .build());
        }
        return ResponseEntity.ok(logbookDtoList);
    }

    @PutMapping
    public ResponseEntity<?> updateLogbook(@RequestBody Logbook newLogbook) {
        if (logbookService.read(newLogbook.getId()).isPresent()) {
            logbookService.update(newLogbook);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteLogbook(@RequestBody LogbookKey id) {
        if (logbookService.read(id).isPresent()) {
            logbookService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
