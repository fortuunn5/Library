package com.example.Library.controller;

import com.example.Library.dto.BookDto;
import com.example.Library.model.Book;
import com.example.Library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<BookDto> readBook(@PathVariable(name="idBook") Long id) {
        if(bookService.read(id).isPresent()) {
            Book book = bookService.read(id).orElseThrow();
            BookDto bookDto = new BookDto(book.getId(), book.getName(), book.getAuthor(), book.getYear(), book.getIsArchived());
            return ResponseEntity.ok(bookDto);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> readBooks() {
        if(!bookService.readAll().isEmpty()) {
            List<Book> books = bookService.readAll();
            List<BookDto> bookDtos = new ArrayList<>();
            for(int i=0; i<books.size(); i++) {
                bookDtos.add(new BookDto(books.get(i).getId(), books.get(i).getName(),books.get(i).getAuthor(), books.get(i).getYear(), books.get(i).getIsArchived()));
            }
            return ResponseEntity.ok(bookDtos);
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
