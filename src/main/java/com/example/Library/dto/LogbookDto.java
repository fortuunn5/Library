package com.example.Library.dto;

import com.example.Library.model.LogbookKey;
import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogbookDto {
    private LogbookKey id;
    ReaderDto reader;
    BookDto book;
    private Calendar issueDate;
    private Calendar deliveryDate;
    private Boolean isArchived;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogbookDto that = (LogbookDto) o;
        return Objects.equals(reader, that.reader) && Objects.equals(book, that.book) && Objects.equals(issueDate, that.issueDate) && Objects.equals(deliveryDate, that.deliveryDate) && Objects.equals(isArchived, that.isArchived);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reader, book, issueDate, deliveryDate, isArchived);
    }
}
