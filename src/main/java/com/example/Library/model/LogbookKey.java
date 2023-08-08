package com.example.Library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class LogbookKey implements Serializable {
    @Column(name="reader_id")
    private Long readerId;
    @Column(name="book_id")
    private Long bookId;

    @Override
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
    }

}
