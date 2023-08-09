package com.example.Library.controller;

import com.example.Library.dto.BookDto;
import com.example.Library.dto.LogbookDto;
import com.example.Library.dto.ReaderDto;
import com.example.Library.model.Logbook;
import com.example.Library.model.LogbookKey;
import com.example.Library.model.Reader;
import com.example.Library.service.BookService;
import com.example.Library.service.LogbookService;
import com.example.Library.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LogbookController {
    private final LogbookService logbookService;
    private final ReaderService readerService;
    private final BookService bookService;

    @PostMapping("/logbooks")
    public ResponseEntity<?> createLogbook(@RequestBody Logbook newLogbook) {

        List<Logbook> logbooks = logbookService.readAll();
        for(int i=0; i<logbooks.size(); i++) {
            if(logbooks.get(i).getReader().equals(newLogbook.getReader())
                    && logbooks.get(i).getBook().equals(newLogbook.getBook())
                    && logbooks.get(i).getIsArchived().equals(newLogbook.getIsArchived())
                    && logbooks.get(i).getIssueDate().equals(newLogbook.getIssueDate())
                    && logbooks.get(i).getDeliveryDate().equals(newLogbook.getDeliveryDate())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        logbookService.create(newLogbook);
        return new ResponseEntity<>(HttpStatus.CREATED);

        /*if(logbookService.read(newLogbook.getId()).isEmpty()) {
            logbookService.create(newLogbook);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);*/
    }

    @GetMapping("/logbooks/{id}")
    public ResponseEntity<LogbookDto> readLogbook(@PathVariable(name="id") LogbookKey id) {
        if(logbookService.read(id).isPresent()) {
            Logbook logbook = logbookService.read(id).orElseThrow();
            ReaderDto readerDto = new ReaderDto(logbook.getReader().getId(),
                                                logbook.getReader().getFio(),
                                                logbook.getReader().getEmail(),
                                                logbook.getReader().getUsername(),
                                                logbook.getReader().getIsArchived());
            BookDto bookDto = new BookDto(logbook.getBook().getId(),
                                          logbook.getBook().getName(),
                                          logbook.getBook().getAuthor(),
                                          logbook.getBook().getYear(),
                                          logbook.getBook().getIsArchived());
            return ResponseEntity.ok(new LogbookDto(logbook.getId(), readerDto, bookDto, logbook.getIssueDate(), logbook.getDeliveryDate(), logbook.getIsArchived()));

            //logbookService.read(id);
            //return ResponseEntity.ok(logbookService.read(id).orElseThrow());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/logbooks")
    public ResponseEntity<List<LogbookDto>> readLogbooks() {
        if(!logbookService.readAll().isEmpty()) {
            List<Logbook> logbooks = logbookService.readAll();
            List<LogbookDto> logbookDtos = new ArrayList<>();
            //ReaderDto readerDto = new ReaderDto();
            //BookDto bookDto = new BookDto();

            for(int i=0; i<logbooks.size(); i++) {
                ReaderDto readerDto = new ReaderDto(logbooks.get(i).getReader().getId(),
                        logbooks.get(i).getReader().getFio(),
                        logbooks.get(i).getReader().getEmail(),
                        logbooks.get(i).getReader().getUsername(),
                        logbooks.get(i).getReader().getIsArchived());
                //readerDto.setFio(logbooks.get(i).getReader().getFio());
                //readerDto.setEmail(logbooks.get(i).getReader().getEmail());
                //readerDto.setUsername(logbooks.get(i).getReader().getUsername());
                //readerDto.setIsArchived(logbooks.get(i).getReader().getIsArchived());

                BookDto bookDto = new BookDto(logbooks.get(i).getBook().getId(),
                        logbooks.get(i).getBook().getName(),
                        logbooks.get(i).getBook().getAuthor(),
                        logbooks.get(i).getBook().getYear(),
                        logbooks.get(i).getBook().getIsArchived());

                //bookDto.setName(logbooks.get(i).getBook().getName());
                //bookDto.setAuthor(logbooks.get(i).getBook().getAuthor());
                //bookDto.setYear(logbooks.get(i).getBook().getYear());
                //bookDto.setIsArchived(logbooks.get(i).getBook().getIsArchived());

                logbookDtos.add(new LogbookDto(logbooks.get(i).getId(),
                        readerDto,
                        bookDto,
                        logbooks.get(i).getIssueDate(),
                        logbooks.get(i).getDeliveryDate(),
                        logbooks.get(i).getIsArchived()));
            }

            return ResponseEntity.ok(logbookDtos);
            //bookService.readAll();
            //return ResponseEntity.ok(logbookService.readAll());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Logbook>> readReadersLogbooks(@PathVariable(name="id") Long id) {
        Reader reader = readerService.read(id).orElseThrow();
        ReaderDto readerDto = new ReaderDto(reader.getId(), reader.getFio(), reader.getEmail(), reader.getUsername(), reader.getIsArchived());
        List<Logbook> logbooks = logbookService.readAll();
        List<Logbook> cur;
        List<LogbookDto> logbookDtos=new ArrayList<>();
        for(int i=0; i<logbooks.size(); i++) {
            if(logbooks.get(i).getReader().equals(reader)) {
                LogbookDto logbookDto = new LogbookDto()
            }
        }


        //for(int i=0; i<reader.)
    }

    @PutMapping("/logbooks/{id}")
    public ResponseEntity<?> updateLogbook(@PathVariable(name = "id") LogbookKey id, Logbook newLogbook) {
        if (id.equals(newLogbook.getId())) {
            if (logbookService.read(id).isPresent()) {
                logbookService.update(newLogbook);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/logbooks/{id}")
    public ResponseEntity<?> deleteLogbook(@PathVariable(name = "id") LogbookKey id) {
        if(logbookService.read(id).isPresent()) {
            logbookService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
