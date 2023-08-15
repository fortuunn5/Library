package com.example.Library.service.impl;

import com.example.Library.dto.BookDto;
import com.example.Library.dto.LogbookDto;
import com.example.Library.dto.ReaderDto;
import com.example.Library.model.Book;
import com.example.Library.model.Logbook;
import com.example.Library.model.LogbookKey;
import com.example.Library.model.Reader;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.LogbookRepository;
import com.example.Library.repository.ReaderRepository;
import com.example.Library.service.interfaces.ReaderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogbookServiceImplTest {
    @Mock
    private LogbookRepository logbookRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private ReaderRepository readerRepository;
    @Mock
    private ReaderService readerService;
    @InjectMocks
    private LogbookServiceImpl logbookService;

    @Test
    void create() {
        Reader reader = Reader.builder()
                .id(1L)
                .fio("A")
                .email("a@ex.com")
                .username("AAA")
                .build();
        Book book = Book.builder()
                .id(1L)
                .name("B")
                .author("B")
                .year(2011)
                .isArchived(false)
                .build();
        Logbook logbook = Logbook.builder()
                .id(new LogbookKey(reader.getId(), book.getId()))
                .reader(reader)
                .book(book)
                .build();

        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        logbookService.create(logbook.getId());
        Mockito.verify(logbookRepository).save(any());
    }

    @Test
    void read() {
        Logbook logbook = mock(Logbook.class);
        LogbookKey id = new LogbookKey(1L, 1L);
        logbook.setId(id);

        when(logbookRepository.findById(id)).thenReturn(Optional.of(logbook));

        Logbook logbook1 = logbookService.read(id);

        assertNotNull(logbook1);
        assertEquals(logbook, logbook1);
        verify(logbookRepository).findById(id);
    }

    @Test
    void readDto() {
        Reader reader = Reader.builder()
                .id(1L)
                .fio("A")
                .email("a@ex.com")
                .username("AAA")
                .build();
        Book book = Book.builder()
                .name("B")
                .author("B")
                .year(2011)
                .isArchived(false)
                .build();
        LogbookKey id = new LogbookKey(1L, 1L);
        Logbook logbook = Logbook.builder()
                .id(id)
                .reader(reader)
                .book(book)
                .build();

        when(logbookRepository.findById(id)).thenReturn(Optional.ofNullable(logbook));
        LogbookDto logbookDto = logbookService.readDto(id);

        assertEquals(logbook.getId(), logbookDto.getId());
        assertEquals(logbook.getReader().getEmail(), logbookDto.getReader().getEmail());
        assertEquals(logbook.getBook().getName(), logbookDto.getBook().getName());
        assertEquals(logbook.getBook().getAuthor(), logbookDto.getBook().getAuthor());
        assertEquals(logbook.getBook().getYear(), logbookDto.getBook().getYear());
        assertEquals(logbook.getIssueDate(), logbookDto.getIssueDate());
        assertEquals(logbook.getDeliveryDate(), logbookDto.getDeliveryDate());
        assertEquals(logbook.getIsArchived(), logbookDto.getIsArchived());
        verify(logbookRepository).findById(id);
    }

    @Test
    void readAll() {
        Reader reader = Reader.builder()
                .id(1L)
                .fio("A")
                .email("a@ex.com")
                .username("A")
                .build();
        Reader reader1 = Reader.builder()
                .id(2L)
                .fio("B")
                .email("b@ex.com")
                .username("B")
                .build();
        Reader reader2 = Reader.builder()
                .id(3L)
                .fio("C")
                .email("c@ex.com")
                .username("C")
                .build();
        Book book = Book.builder()
                .name("A")
                .author("A")
                .year(2023)
                .isArchived(false)
                .build();
        Book book1 = Book.builder()
                .name("B")
                .author("B")
                .year(2011)
                .isArchived(false)
                .build();
        Book book2 = Book.builder()
                .name("C")
                .author("C")
                .year(1990)
                .isArchived(false)
                .build();

        Logbook logbook = Logbook.builder()
                .id(new LogbookKey(reader.getId(), book.getId()))
                .reader(reader)
                .book(book)
                .build();
        Logbook logbook1 = Logbook.builder()
                .id(new LogbookKey(reader1.getId(), book1.getId()))
                .reader(reader1)
                .book(book1)
                .build();
        Logbook logbook2 = Logbook.builder()
                .id(new LogbookKey(reader2.getId(), book2.getId()))
                .reader(reader2)
                .book(book2)
                .build();

        List<Logbook> logbooks = new ArrayList<>();
        logbooks.add(logbook);
        logbooks.add(logbook1);
        logbooks.add(logbook2);

        when(logbookRepository.findAll()).thenReturn(logbooks);

        List<Logbook> logbookList = logbookService.readAll();
        assertEquals(logbooks.size(), logbookList.size());
        verify(logbookRepository).findAll();
    }

    @Test
    void readAllDto() {
        Reader reader = Reader.builder()
                .id(1L)
                .fio("A")
                .email("a@ex.com")
                .username("A")
                .build();
        Reader reader1 = Reader.builder()
                .id(2L)
                .fio("B")
                .email("b@ex.com")
                .username("B")
                .build();
        Reader reader2 = Reader.builder()
                .id(3L)
                .fio("C")
                .email("c@ex.com")
                .username("C")
                .build();
        Book book = Book.builder()
                .name("A")
                .author("A")
                .year(2023)
                .isArchived(false)
                .build();
        Book book1 = Book.builder()
                .name("B")
                .author("B")
                .year(2011)
                .isArchived(false)
                .build();
        Book book2 = Book.builder()
                .name("C")
                .author("C")
                .year(1990)
                .isArchived(false)
                .build();

        Logbook logbook = Logbook.builder()
                .id(new LogbookKey(reader.getId(), book.getId()))
                .reader(reader)
                .book(book)
                .build();
        Logbook logbook1 = Logbook.builder()
                .id(new LogbookKey(reader1.getId(), book1.getId()))
                .reader(reader1)
                .book(book1)
                .build();
        Logbook logbook2 = Logbook.builder()
                .id(new LogbookKey(reader2.getId(), book2.getId()))
                .reader(reader2)
                .book(book2)
                .build();

        List<Logbook> logbooks = new ArrayList<>();
        logbooks.add(logbook);
        logbooks.add(logbook1);
        logbooks.add(logbook2);

        when(logbookRepository.findAll()).thenReturn(logbooks);

        List<LogbookDto> logbookDtoList = logbookService.readAllDto();

        assertEquals(logbooks.size(), logbookDtoList.size());
        verify(logbookRepository).findAll();
    }

    @Test
    void update() {
        Reader reader = Reader.builder()
                .id(1L)
                .fio("A")
                .email("a@ex.com")
                .username("A")
                .build();
        Reader reader1 = Reader.builder()
                .id(1L)
                .fio("B")
                .email("b@ex.com")
                .username("B")
                .build();
        Book book = Book.builder()
                .id(1L)
                .name("A")
                .author("A")
                .year(2023)
                .isArchived(false)
                .build();
        Book book1 = Book.builder()
                .id(1L)
                .name("B")
                .author("B")
                .year(2011)
                .isArchived(false)
                .build();

        Logbook logbook = Logbook.builder()
                .id(new LogbookKey(reader.getId(), book.getId()))
                .reader(reader)
                .book(book)
                .build();
        Logbook logbook1 = Logbook.builder()
                .id(new LogbookKey(reader1.getId(), book1.getId()))
                .reader(reader1)
                .book(book1)
                .build();

        logbookRepository.save(logbook);

        when(logbookRepository.findById(new LogbookKey(1L, 1L))).thenReturn(Optional.of(logbook));

        logbookService.update(logbook1);
        verify(logbookRepository, times(2)).save(logbook);

    }

    @Test
    void delete() {
        Reader reader = Reader.builder()
                .id(1L)
                .fio("A")
                .email("a@ex.com")
                .username("A")
                .build();
        Book book = Book.builder()
                .id(1L)
                .name("A")
                .author("A")
                .year(2023)
                .isArchived(false)
                .build();

        Logbook logbook = Logbook.builder()
                .id(new LogbookKey(reader.getId(), book.getId()))
                .reader(reader)
                .book(book)
                .isArchived(false)
                .build();

        when(logbookRepository.findById(new LogbookKey(1L, 1L))).thenReturn(Optional.ofNullable(logbook));

        logbookService.delete(new LogbookKey(1L, 1L));

        assertEquals(logbook.getIsArchived(),true);
        Mockito.verify(logbookRepository).save(logbook);
    }

    @Test
    void getOverdueDays() {
        Calendar start = new GregorianCalendar(2011, 05, 10);
        Calendar end = new GregorianCalendar(2011, 06, 20);
        long count = logbookService.getOverdueDays(start, end);
        assertEquals(count, 10);
    }

    @Test
    void getPenalties() {
        long overDays = 23;
        float pen = logbookService.getPenalties(overDays);
        double penL = (double) Math.round(pen * 100) /100;
        assertEquals(penL, 0.96);
    }
}