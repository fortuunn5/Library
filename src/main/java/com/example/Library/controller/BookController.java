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

        List<Book> books = bookService.readAll();
        for (Book book : books) {
            if (book.equals(newBook)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        bookService.create(newBook);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{idBook}")
    public ResponseEntity<BookDto> readBook(@PathVariable Long idBook) {
        if(bookService.read(idBook).isPresent()) {
            Book book = bookService.read(idBook).orElseThrow();
            BookDto bookDto = BookDto.builder()
                    .id(book.getId())
                    .name(book.getName())
                    .author(book.getAuthor())
                    .year(book.getYear())
                    .isArchived(book.getIsArchived())
                    .build();
            return ResponseEntity.ok(bookDto);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> readBooks() {
        if(!bookService.readAll().isEmpty()) {
            List<Book> books = bookService.readAll();
            List<BookDto> bookDtoList = new ArrayList<>();
            for (Book book : books) {
                bookDtoList.add(BookDto.builder()
                        .id(book.getId())
                        .name(book.getName())
                        .author(book.getAuthor())
                        .year(book.getYear())
                        .isArchived(book.getIsArchived())
                        .build()
                );
            }
            return ResponseEntity.ok(bookDtoList);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> updateBook(@RequestBody Book upBook) {
        if (bookService.read(upBook.getId()).isPresent()) {
            bookService.update(upBook);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{idBook}")
    public ResponseEntity<?> deleteBook(@PathVariable(name="idBook") Long id) {
        if(bookService.read(id).isPresent()) {
            bookService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
