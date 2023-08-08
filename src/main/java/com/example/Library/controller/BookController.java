package com.example.Library.controller;

import com.example.Library.model.Book;
import com.example.Library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/readers/{id}")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/books")
    public ResponseEntity<?> createBook(@RequestBody Book newBook) {

        List<Book> books = bookService.readAll();
        for(int i=0; i<books.size(); i++) {
            if(books.get(i).getName().equals(newBook.getName())
                    && books.get(i).getAuthor().equals(newBook.getAuthor())
                    && books.get(i).getYear().equals(newBook.getYear())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        bookService.create(newBook);
        return new ResponseEntity<>(HttpStatus.CREATED);

        /*bookService.create(newBook);
        return new ResponseEntity<>(HttpStatus.CREATED);*/
        //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/books/{idBook}")
    public ResponseEntity<Book> readBook(@PathVariable(name="idBook") Long id) {
        if(bookService.read(id).isPresent()) {
            bookService.read(id);
            return ResponseEntity.ok(bookService.read(id).orElseThrow());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> readBooks() {
        if(!bookService.readAll().isEmpty()) {
            //bookService.readAll();
            return ResponseEntity.ok(bookService.readAll());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/books/{idBook}")
    public ResponseEntity<?> updateBook(@PathVariable(name="idBook") Long id, @RequestBody Book upBook) {
        if(id.equals(upBook.getId())) {
            if (bookService.read(id).isPresent()) {
                bookService.update(upBook);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/books/{idBook}")
    public ResponseEntity<?> deleteBook(@PathVariable(name="idBook") Long id) {
        if(bookService.read(id).isPresent()) {
            bookService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
