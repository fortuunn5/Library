package com.example.Library.repository;

import com.example.Library.model.Logbook;
import com.example.Library.model.LogbookKey;
import com.example.Library.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogbookRepository extends JpaRepository<Logbook, LogbookKey> {
    List<Logbook> findByReader(Reader reader);
}
