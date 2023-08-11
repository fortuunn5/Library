package com.example.Library.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String name;
    private String author;
    private Integer year;
    private Boolean isArchived;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(name, bookDto.name) && Objects.equals(author, bookDto.author) && Objects.equals(year, bookDto.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, year);
    }
}
