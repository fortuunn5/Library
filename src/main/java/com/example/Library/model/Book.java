package com.example.Library.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private Long id;
    @NonNull
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="author", nullable = false)
    private String author;
    @Column(name="year", nullable = false)
    private Integer year;
    @Column(nullable = false)
    private Boolean isArchived = false;

    @OneToMany(mappedBy = "book")
    //@JoinColumn(name="book_id")
    private Set<Logbook> logbooks/*=null*/;

    public Book(@NonNull String name, String author, Integer year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(name, book.name) && Objects.equals(author, book.author) && Objects.equals(year, book.year) && Objects.equals(isArchived, book.isArchived);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, year, isArchived);
    }
}
