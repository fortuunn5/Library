package com.example.Library.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
public class Logbook {
    @EmbeddedId
    private LogbookKey id= new LogbookKey();

    @ManyToOne
    @MapsId("readerId")
    @JoinColumn(name="reader_id")
    Reader reader;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name="book_id")
    Book book;

    @Column(name="issueDate", nullable = false)
    private Calendar issueDate /*= new GregorianCalendar()*/;

    @Column(name="deliveryDate")
    private Calendar deliveryDate = null;

    @Column(nullable = false)
    private Boolean isArchived = false;

    public Logbook(Reader reader, Book book, Calendar issueDate, Calendar deliveryDate, Boolean isArchived) {
        this.id = new LogbookKey(reader.getId(), book.getId());
        this.reader = reader;
        this.book = book;
        this.issueDate = issueDate;
        this.deliveryDate = deliveryDate;
        this.isArchived = isArchived;
    }

    public Logbook(Reader reader, Book book) {
        this.id = new LogbookKey(reader.getId(), book.getId());
        this.reader = reader;
        this.book = book;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logbook logbook = (Logbook) o;
        return Objects.equals(reader, logbook.reader) && Objects.equals(book, logbook.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reader, book);
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logbook logbook = (Logbook) o;
        return Objects.equals(reader, logbook.reader) && Objects.equals(book, logbook.book) && Objects.equals(issueDate, logbook.issueDate) && Objects.equals(deliveryDate, logbook.deliveryDate) && Objects.equals(isArchived, logbook.isArchived);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reader, book, issueDate, deliveryDate, isArchived);
    }
}
