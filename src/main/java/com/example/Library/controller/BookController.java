package com.example.Library.controller;

import com.example.Library.dto.BookDto;
import com.example.Library.model.Book;
import com.example.Library.service.interfaces.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody Book newBook) {
        bookService.create(newBook);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{idBook}")
    public ResponseEntity<BookDto> readBookDto(@PathVariable Long idBook) {
        return ResponseEntity.ok(bookService.readDto(idBook));
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> readBooks() {
            return ResponseEntity.ok(bookService.readAll());
    }

    @PutMapping
    public ResponseEntity<?> updateBook(@RequestBody Book upBook) {
        bookService.update(upBook);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{idBook}")
    public ResponseEntity<?> deleteBook(@PathVariable(name="idBook") Long id) {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
