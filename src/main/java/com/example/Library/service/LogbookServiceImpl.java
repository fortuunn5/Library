package com.example.Library.service;

import com.example.Library.dto.LogbookDto;
import com.example.Library.model.Logbook;
import com.example.Library.model.LogbookKey;
import com.example.Library.model.Reader;
import com.example.Library.repository.LogbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogbookServiceImpl implements LogbookService {
    private final LogbookRepository logbookRepository;
    @Override
    public void create(Logbook newLogbook) {
        /*newLogbook.setIsArchived(false);
        newLogbook.setIssueDate(Calendar.getInstance());
        logbookRepository.save(newLogbook);*/

        List<Logbook> logbooks = logbookRepository.findAll();
        boolean isExist=false;
        for(int i=0; i<logbooks.size(); i++) {
            if(logbooks.get(i).getReader().equals(newLogbook.getReader())
                    && logbooks.get(i).getBook().equals(newLogbook.getBook())
                    && logbooks.get(i).getIsArchived().equals(newLogbook.getIsArchived())
                    && logbooks.get(i).getIssueDate().equals(newLogbook.getIssueDate())
                    && logbooks.get(i).getDeliveryDate().equals(newLogbook.getDeliveryDate())) {
                isExist=true;
            }
        }
        if (!isExist) {
            //newLogbook.setIsArchived(false);
            //newLogbook.setIssueDate(Calendar.getInstance());
            logbookRepository.save(newLogbook);
        }

    }

    @Override
    public Optional<Logbook> read(LogbookKey id) {
        return logbookRepository.findById(id);
    }

    @Override
    public List<Logbook> readAll() {
        return logbookRepository.findAll();
    }
    @Override
    public List<Logbook> readReadersLogbooks(Reader reader) {
        List<Logbook> logbooks = logbookRepository.findAll();
        List<Logbook> curLogbooks = new ArrayList<>();
        for(int i=0; i<logbooks.size(); i++) {
            if(logbooks.get(i).getReader().equals(reader)) {
                curLogbooks.add(logbooks.get(i));
            }
        }
        return curLogbooks;
    }

    @Override
    public void update(Logbook updateLogbook) {
        Logbook logbook = logbookRepository.findById(updateLogbook.getId()).orElseThrow();

        if(updateLogbook.getReader() != null) {
            logbook.setReader(updateLogbook.getReader());
        }
        if(updateLogbook.getBook() != null) {
            logbook.setBook(updateLogbook.getBook());
        }
        if(updateLogbook.getIssueDate() != null)  {
            logbook.setIssueDate(updateLogbook.getIssueDate());
        }
        if(updateLogbook.getDeliveryDate() != null) {
            logbook.setDeliveryDate(updateLogbook.getDeliveryDate());
        }
        if(updateLogbook.getIsArchived()!=null) {
            logbook.setIsArchived(updateLogbook.getIsArchived());
        }
        logbookRepository.save(logbook);
    }

    @Override
    public void delete(LogbookKey id) {
        Logbook logbook = logbookRepository.findById(id).orElseThrow();
        logbook.setDeliveryDate(Calendar.getInstance());
        logbook.setIsArchived(true);
        logbookRepository.save(logbook);
    }
}
