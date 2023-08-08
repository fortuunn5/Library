package com.example.Library.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.Objects;

@Entity
@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Logbook {
    @EmbeddedId
    private LogbookKey id= new LogbookKey();

    @ManyToOne
    @MapsId("readerId")
    @JoinColumn(name="reader-id")
    private Reader reader;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name="book-id")
    private Book book;

    @Column(name="issueDate", nullable = false)
    private Calendar issueDate;

    @Column(name="deliveryDate", nullable = false)
    private Calendar deliveryDate;

    @Column(nullable = false)
    private Boolean isArchived = false;

    public Logbook(Reader reader, Book book, Calendar issueDate, Calendar deliveryDate, Boolean isArchived) {
        this.reader = reader;
        this.book = book;
        this.issueDate = issueDate;
        this.deliveryDate = deliveryDate;
        this.isArchived = isArchived;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logbook logbook = (Logbook) o;
        return Objects.equals(reader, logbook.reader) && Objects.equals(book, logbook.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reader, book);
    }
}