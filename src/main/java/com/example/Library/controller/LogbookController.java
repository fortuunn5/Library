package com.example.Library.controller;

import com.example.Library.model.Logbook;
import com.example.Library.model.LogbookKey;
import com.example.Library.service.LogbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LogbookController {
    private final LogbookService logbookService;

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
    public ResponseEntity<Logbook> readLogbook(@PathVariable(name="id") LogbookKey id) {
        if(logbookService.read(id).isPresent()) {
            logbookService.read(id);
            return ResponseEntity.ok(logbookService.read(id).orElseThrow());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/logbooks")
    public ResponseEntity<List<Logbook>> readLogbooks() {
        if(!logbookService.readAll().isEmpty()) {
            //bookService.readAll();
            return ResponseEntity.ok(logbookService.readAll());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
