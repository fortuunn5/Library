package com.example.Library.service;

import com.example.Library.dto.BookDto;
import com.example.Library.model.Book;
import com.example.Library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    @Override
    public void create(Book newBook) {
        List<Book> books = bookRepository.findAll();
        boolean isExist=false;
        for(int i=0; i<books.size(); i++) {
            if(books.get(i).getName().equals(newBook.getName())
                    && books.get(i).getAuthor().equals(newBook.getAuthor())
                    && books.get(i).getYear().equals(newBook.getYear())) {
                isExist=true;
            }
        }
        if (!isExist) {
            bookRepository.save(newBook);
        }
    }

    @Override
    public Optional<Book> read(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> readAll() {
        return bookRepository.findAll();
    }

    @Override
    public void update(Book updateBook) {
        Book book = bookRepository.findById(updateBook.getId()).orElseThrow();

        if(updateBook.getName() != null && !updateBook.getName().isBlank()) {
            book.setName(updateBook.getName());
        }
        if(updateBook.getAuthor() != null && !updateBook.getAuthor().isBlank()) {
            book.setAuthor(updateBook.getAuthor());
        }
        if(updateBook.getYear()!=null) {
            book.setYear(updateBook.getYear());
        }
        bookRepository.save(book);

    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

}
