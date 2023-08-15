package com.example.Library.service.impl;

import com.example.Library.dto.BookDto;
import com.example.Library.model.Book;
import com.example.Library.repository.BookRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void create() {
        Book book = mock(Book.class);
        bookService.create(book);
        Mockito.verify(bookRepository).save(book);

    }

    @Test
    void read() {
        Book book = mock(Book.class);
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book book1 = bookService.read(1L);

        assertNotNull(book1);
        assertEquals(book, book1);
        verify(bookRepository).findById(1L);
    }

    @Test
    void readDto() {
        //Book book = mock(Book.class);
        Book book = Book.builder()
                .id(1L)
                .name("A")
                .author("A")
                .year(2000)
                .build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookDto bookDto = bookService.readDto(1L);

        assertEquals(book.getId(), bookDto.getId());
        assertEquals(book.getName(), bookDto.getName());
        assertEquals(book.getAuthor(), bookDto.getAuthor());
        assertEquals(book.getYear(), bookDto.getYear());
        assertEquals(book.getIsArchived(), bookDto.getIsArchived());
        verify(bookRepository).findById(1L);
    }

    @Test
    void readAll() {
        Book book = Book.builder()
                .id(1L)
                .name("A")
                .author("A")
                .year(2000)
                .build();
        Book book2 = Book.builder()
                .id(2L)
                .name("B")
                .author("B")
                .year(1993)
                .build();
        Book book3 = Book.builder()
                .name("C")
                .author("C")
                .year(1947)
                .build();
        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(book2);
        books.add(book3);

        when(bookRepository.findAll()).thenReturn(books);

        List<BookDto> bookDtoList = bookService.readAll();

        assertEquals(books.size(), bookDtoList.size());
        verify(bookRepository).findAll();
    }

    @Test
    void update() {
        Book book = Book.builder()
                .id(1L)
                .name("A")
                .author("A")
                .year(2020)
                .isArchived(false)
                .build();
        bookRepository.save(book);

        Book upBook = Book.builder()
                .id(1L)
                .name("B")
                .author("B")
                .year(2021)
                .isArchived(false)
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.update(upBook);
        verify(bookRepository, times(2)).save(book);

    }

    @Test
    void delete() {
        Book book = Book.builder()
                .id(1L)
                .name("A")
                .author("A")
                .year(2020)
                .isArchived(false)
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.delete(book.getId());
        assertEquals(book.getIsArchived(),true);
        Mockito.verify(bookRepository).save(book);
    }
}