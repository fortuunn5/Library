package com.example.Library.service.impl;

import com.example.Library.model.Logbook;
import com.example.Library.model.LogbookKey;
import com.example.Library.model.Reader;
import com.example.Library.repository.LogbookRepository;
import com.example.Library.service.interfaces.LogbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LogbookServiceImpl implements LogbookService {
    private final LogbookRepository logbookRepository;
    @Override
    public void create(Logbook newLogbook) {
        /*newLogbook.setIsArchived(false);
        newLogbook.setIssueDate(Calendar.getInstance());
        logbookRepository.save(newLogbook);*/

        //TODO: инвертировать, слишком тяжело читать
        if(newLogbook.getReader().getIsArchived() || newLogbook.getBook().getIsArchived()) {
            return;
        }
        List<Logbook> logbooks = logbookRepository.findAll();
        boolean isExist = false;
        for (int i = 0; i < logbooks.size(); i++) { //TODO: equals
            /*if (logbooks.get(i).getReader().equals(newLogbook.getReader()) &&
                    logbooks.get(i).getBook().equals(newLogbook.getBook()) &&
                    logbooks.get(i).getIsArchived().equals(newLogbook.getIsArchived()) &&
                    logbooks.get(i).getIssueDate().equals(newLogbook.getIssueDate()) &&
                    logbooks.get(i).getDeliveryDate().equals(newLogbook.getDeliveryDate())) {
                    isExist = true;
            }*/
            if(logbooks.get(i).equals(newLogbook))
                isExist = true;
        }
        if (!isExist) {
            newLogbook.setIsArchived(false);
            newLogbook.setIssueDate(Calendar.getInstance());
            newLogbook.setDeliveryDate(null);
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
        return logbookRepository.findByReader(reader);
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
        /*if(updateLogbook.getDeliveryDate() != null) {
            logbook.setDeliveryDate(updateLogbook.getDeliveryDate());
        }
        if(updateLogbook.getIsArchived()!=null) {
            logbook.setIsArchived(updateLogbook.getIsArchived());
        }*/
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
        Logbook logbook = logbookRepository.findById(id).orElseThrow();
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
        float pen=0;
        if(overDays>14) {
            pen = (float) (14*0.03+(overDays-14)*0.06);
        }
        else
            pen = (float) (overDays*0.03);
        return pen;
    }
}
