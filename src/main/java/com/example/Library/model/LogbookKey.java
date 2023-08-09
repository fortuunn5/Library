package com.example.Library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogbookKey implements Serializable {
    @Column(name="reader_id")
    Long readerId;
    @Column(name="book_id")
    Long bookId;

    /*@Override
    public int hashCode() {
        long hash = readerId + bookId;
        return (int) hash;
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj.getClass()!=this.getClass()) {
            return false;
        }
        return (Objects.equals(readerId, ((LogbookKey) obj).getReaderId()) && Objects.equals(bookId, ((LogbookKey) obj).bookId));
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogbookKey that = (LogbookKey) o;
        return Objects.equals(readerId, that.readerId) && Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(readerId, bookId);
    }
}
