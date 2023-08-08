package com.example.Library.repository;

import com.example.Library.model.Logbook;
import com.example.Library.model.LogbookKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogbookRepository extends JpaRepository<Logbook, LogbookKey> {
}
