package com.example.Library.service.impl;

import com.example.Library.exception.NotFoundException;
import com.example.Library.dto.BookDto;
import com.example.Library.model.Book;
import com.example.Library.repository.BookRepository;
import com.example.Library.service.interfaces.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public void create(Book newBook) {
        List<Book> books = bookRepository.findAll();
        boolean isExist;
        isExist = books.stream().anyMatch(x -> x.equals(newBook));
        if (!isExist) {
            bookRepository.save(newBook);
        }
        else
            throw new IllegalArgumentException();
    }

    @Override
    public Book read(Long id) {
        return bookRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public BookDto readDto(Long id){
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        return BookDto.builder()
                .id(id)
                .name(book.getName())
                .author(book.getAuthor())
                .year(book.getYear())
                .isArchived(book.getIsArchived())
                .build();
    }

    @Override
    public List<BookDto> readAll() {
        List<Book> books = bookRepository.findAll();
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
        return bookDtoList;
    }

    @Override
    public void update(Book updateBook) {
        Book book = bookRepository.findById(updateBook.getId()).orElseThrow(NotFoundException::new);

        if(updateBook.getName() != null && !updateBook.getName().isBlank()) {
            book.setName(updateBook.getName());
        }
        if(updateBook.getAuthor() != null && !updateBook.getAuthor().isBlank()) {
            book.setAuthor(updateBook.getAuthor());
        }
        if(updateBook.getYear()!=null) {
            book.setYear(updateBook.getYear());
        }
        if(updateBook.getIsArchived()!=null) {
            book.setIsArchived((updateBook.getIsArchived()));
        }
        bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        book.setIsArchived(true);
        bookRepository.save(book);
    }

}
