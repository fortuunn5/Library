package com.example.Library.service.impl;

import com.example.Library.exception.ArchiveException;
import com.example.Library.exception.NotFoundException;
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
import com.example.Library.service.interfaces.LogbookService;
import com.example.Library.service.interfaces.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LogbookServiceImpl implements LogbookService {
    private final LogbookRepository logbookRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final ReaderService readerService;

    @Override
    public void create(LogbookKey newLogbookKey) {
        Reader reader = readerRepository.findById(newLogbookKey.getReaderId()).orElseThrow(NotFoundException::new);
        Book book = bookRepository.findById(newLogbookKey.getBookId()).orElseThrow(NotFoundException::new);

        if(reader.getIsArchived() || book.getIsArchived()) {
            throw new ArchiveException();
        }

        List<Logbook> logbooks = logbookRepository.findAll();
        boolean isExist = false;
        for (Logbook logbook : logbooks) {
            if (logbook.equals(logbookRepository.findById(newLogbookKey).orElseThrow(NotFoundException::new))) {
                isExist = true;
            }
        }
        if(!isExist) {
            Logbook newLogbook = Logbook.builder()
                    .id(newLogbookKey)
                    .reader(reader)
                    .book(book)
                    .build();
            logbookRepository.save(newLogbook);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Logbook read(LogbookKey id) {
        return logbookRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public LogbookDto readDto(LogbookKey id) {
        Logbook logbook = logbookRepository.findById(id).orElseThrow(NotFoundException::new);
        ReaderDto readerDto = ReaderDto.builder()
                .id(logbook.getReader().getId())
                .fio(logbook.getReader().getFio())
                .email(logbook.getReader().getEmail())
                .username(logbook.getReader().getUsername())
                .isArchived(logbook.getReader().getIsArchived())
                .build();
        BookDto bookDto = BookDto.builder()
                .id(logbook.getBook().getId())
                .name(logbook.getBook().getName())
                .year(logbook.getBook().getYear())
                .isArchived(logbook.getBook().getIsArchived())
                .build();
        return LogbookDto.builder()
                .id(logbook.getId())
                .reader(readerDto)
                .book(bookDto)
                .issueDate(logbook.getIssueDate())
                .deliveryDate(logbook.getDeliveryDate())
                .isArchived(logbook.getIsArchived())
                .build();

    }

    @Override
    public List<Logbook> readAll() {
        return logbookRepository.findAll();
    }

    @Override
    public List<LogbookDto> readAllDto() {

        List<Logbook> logbooks = logbookRepository.findAll();
        List<LogbookDto> logbookDtoList = new ArrayList<>();

        for (Logbook logbook : logbooks) {
            ReaderDto readerDto = ReaderDto.builder()
                    .id(logbook.getReader().getId())
                    .fio(logbook.getReader().getFio())
                    .email(logbook.getReader().getEmail())
                    .username(logbook.getReader().getUsername())
                    .isArchived(logbook.getReader().getIsArchived())
                    .build();

            BookDto bookDto = BookDto.builder()
                    .id(logbook.getBook().getId())
                    .name(logbook.getBook().getName())
                    .author(logbook.getBook().getAuthor())
                    .year(logbook.getBook().getYear())
                    .isArchived(logbook.getBook().getIsArchived())
                    .build();

            logbookDtoList.add(LogbookDto.builder()
                    .id(logbook.getId())
                    .reader(readerDto)
                    .book(bookDto)
                    .issueDate(logbook.getIssueDate())
                    .deliveryDate(logbook.getDeliveryDate())
                    .isArchived(logbook.getIsArchived())
                    .build()
            );
        }
        return logbookDtoList;
    }

    @Override
    public List<LogbookDto> readReadersLogbooks(/*Reader reader*/) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Reader reader = readerService.readByUsername(userDetails.getUsername());

        ReaderDto readerDto = ReaderDto.builder()
                .id(reader.getId())
                .fio(reader.getFio())
                .email(reader.getEmail())
                .username(reader.getUsername())
                .isArchived(reader.getIsArchived())
                .build();
        List<Logbook> logbooks = logbookRepository.findByReader(reader);
        List<LogbookDto> logbookDtoList = new ArrayList<>();

        for (Logbook logbook : logbooks) {

            BookDto bookDto = BookDto.builder()
                    .id(logbook.getBook().getId())
                    .name(logbook.getBook().getName())
                    .author(logbook.getBook().getAuthor())
                    .year(logbook.getBook().getYear())
                    .isArchived(logbook.getBook().getIsArchived())
                    .build();

            logbookDtoList.add(LogbookDto.builder()
                    .id(logbook.getId())
                    .reader(readerDto)
                    .book(bookDto)
                    .issueDate(logbook.getIssueDate())
                    .deliveryDate(logbook.getDeliveryDate())
                    .isArchived(logbook.getIsArchived())
                    .build());
        }
        return logbookDtoList;
    }

    @Override
    public void update(Logbook updateLogbook) {
        Logbook logbook = logbookRepository.findById(updateLogbook.getId()).orElseThrow(NotFoundException::new);

        if(updateLogbook.getReader() != null) {
            logbook.setReader(updateLogbook.getReader());
        }
        if(updateLogbook.getBook() != null) {
            logbook.setBook(updateLogbook.getBook());
        }
        if(updateLogbook.getIssueDate() != null)  {
            logbook.setIssueDate(updateLogbook.getIssueDate());
        }
        if(updateLogbook.getDeliveryDate()!=null) {
            logbook.setDeliveryDate(updateLogbook.getDeliveryDate());
            logbook.setIsArchived(true);
        } else if(!logbook.getIsArchived() && updateLogbook.getIsArchived()) {
            logbook.setIsArchived(true);
            logbook.setDeliveryDate(Calendar.getInstance());
        } else if(logbook.getIsArchived() && !updateLogbook.getIsArchived()) {
            logbook.setIsArchived(false);
            logbook.setDeliveryDate(null);
        }
        logbookRepository.save(logbook);
    }

    @Override
    public void delete(LogbookKey id) throws NoSuchElementException {
        Logbook logbook = logbookRepository.findById(id).orElseThrow(NotFoundException::new);
        if(logbook.getIsArchived()) {
            throw new NoSuchElementException("Формуляр уже удален");
        }
        logbook.setDeliveryDate(Calendar.getInstance());
        logbook.setIsArchived(true);
        logbookRepository.save(logbook);
    }

    @Override
    public long getOverdueDays(Calendar start, Calendar end) {
        long startDate = start.getTimeInMillis();
        long endDate = end.getTimeInMillis();
        long count = TimeUnit.MILLISECONDS.toDays(endDate - startDate);
        if(count<=30)
            return 0;
        return count-30;
    }

    @Override
    public float getPenalties(Long overDays) {
        float pen;
        if(overDays>14) {
            pen = (float) (14*0.03+(overDays-14)*0.06);
        }
        else
            pen = (float) (overDays*0.03);
        return pen;
    }
}
