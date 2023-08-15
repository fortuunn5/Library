package com.example.Library.service.interfaces;

import com.example.Library.dto.BookDto;
import com.example.Library.model.Book;

import java.util.List;

public interface BookService {
    void create(Book newBook);
    Book read(Long id);
    BookDto readDto(Long id);
    List<BookDto> readAll();
    void update(Book updateBook);
    void delete(Long id);
}
