package com.example.Library.service.interfaces;

import com.example.Library.dto.BookDto;
import com.example.Library.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    void create(Book newBook);
    Optional<Book> read(Long id);
    List<Book> readAll();
    void update(Book updateBook);
    void delete(Long id);
}
