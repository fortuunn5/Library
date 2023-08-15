package com.example.Library.service.interfaces;

import com.example.Library.dto.LogbookDto;
import com.example.Library.model.Logbook;
import com.example.Library.model.LogbookKey;
import com.example.Library.model.Reader;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface LogbookService {
    void create(LogbookKey newLogbookKey);
    Logbook read(LogbookKey id);
    LogbookDto readDto(LogbookKey id);
    List<LogbookDto> readAllDto();
    List<Logbook> readAll();
    List<LogbookDto> readReadersLogbooks(/*Reader reader*/);
    void update(Logbook updateLogbook);
    void delete(LogbookKey id);

    long getOverdueDays(Calendar start, Calendar end);
    float getPenalties(Long overDays);
}
