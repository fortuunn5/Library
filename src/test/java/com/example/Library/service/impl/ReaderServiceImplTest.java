package com.example.Library.service.impl;

import com.example.Library.dto.BookDto;
import com.example.Library.dto.ReaderDto;
import com.example.Library.model.Book;
import com.example.Library.model.Reader;
import com.example.Library.repository.ReaderRepository;
import com.example.Library.service.interfaces.ReaderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReaderServiceImplTest {
    @Mock
    private ReaderRepository readerRepository;
    @InjectMocks
    private ReaderServiceImpl readerService;

    @Test
    void create() {
        Reader reader = mock(Reader.class);
        readerService.create(reader);
        Mockito.verify(readerRepository).save(reader);
    }

    @Test
    void read() {
        Reader reader = mock(Reader.class);
        reader.setId(1L);
        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));

        Reader reader1 = readerService.read(1L);

        assertNotNull(reader1);
        assertEquals(reader, reader1);
        verify(readerRepository).findById(1L);
    }

    @Test
    void readDto() {
        Reader reader = Reader.builder()
                .id(1L)
                .fio("A")
                .email("a@ex.com")
                .username("A")
                .isArchived(false)
                .build();
        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));

        ReaderDto readerDto = readerService.readDto(1L);

        assertEquals(reader.getId(), readerDto.getId());
        assertEquals(reader.getFio(), readerDto.getFio());
        assertEquals(reader.getEmail(), readerDto.getEmail());
        assertEquals(reader.getUsername(), readerDto.getUsername());
        assertEquals(reader.getIsArchived(), readerDto.getIsArchived());
        verify(readerRepository).findById(1L);
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
        List<Reader> readers = new ArrayList<>();
        readers.add(reader);
        readers.add(reader1);
        readers.add(reader2);

        when(readerRepository.findAll()).thenReturn(readers);

        List<ReaderDto> readerDtoList = readerService.readAll();

        assertEquals(readers.size(), readerDtoList.size());
        verify(readerRepository).findAll();
    }

    @Test
    void update() {
        Reader reader = Reader.builder()
                .id(1L)
                .fio("A")
                .email("a@ex.com")
                .username("A")
                .build();
        readerRepository.save(reader);
        Reader reader1 = Reader.builder()
                .id(1L)
                .fio("B")
                .email("b@ex.com")
                .username("B")
                .build();

        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));

        readerService.update(reader1);
        verify(readerRepository, times(2)).save(reader);
    }

    @Test
    void delete() {
        Reader reader = Reader.builder()
                .id(1L)
                .fio("A")
                .email("a@ex.com")
                .username("A")
                .build();

        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));

        readerService.delete(reader.getId());
        assertEquals(reader.getIsArchived(),true);
        Mockito.verify(readerRepository).save(reader);
    }

    @Test
    void readByUsername() {
        Reader reader = mock(Reader.class);
        when(readerRepository.findByUsername(reader.getUsername())).thenReturn(Optional.of(reader));

        Reader reader1 = readerService.readByUsername(reader.getUsername());

        assertEquals(reader,reader1);
    }
}