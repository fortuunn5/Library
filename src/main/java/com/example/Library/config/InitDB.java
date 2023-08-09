package com.example.Library.config;

import com.example.Library.model.Book;
import com.example.Library.model.Logbook;
import com.example.Library.model.Reader;
import com.example.Library.service.BookService;
import com.example.Library.service.LogbookService;
import com.example.Library.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Configuration
@RequiredArgsConstructor
public class InitDB {

    private final ReaderService readerService;
    private final BookService bookService;
    private final LogbookService logbookService;
    @Bean
    public void createReader() {
        Reader reader;
        Reader reader1;
        Reader reader2;
        readerService.create(new Reader(
                "Петров Петр Петрович",
                "aaaaa@a.com",
                "aaaa",
                "aaaa"));
        readerService.create(new Reader(
                "Макарова Виктория Александровна",
                "bbbbb@b.com",
                "bbbb",
                "bbbb"
        ));
        readerService.create(new Reader(
                "Александров Леонид Эмирович",
                "ccccc@c.com",
                "cccc",
                "cccc"
        ));

        bookService.create(new Book(
                "Война и мир",
                "Лев Толстой",
                1873
        ));
        bookService.create(new Book(
                "1984",
                "Джордж Оруэлл",
                1949
        ));
        bookService.create(new Book(
                "Лолита",
                "Владимир Набоков",
                1998
        ));
        bookService.create(new Book(
                "Левиафан",
                "Томас Гоббс",
                1998
        ));

        reader=readerService.read(1L).orElseThrow();
        reader1=readerService.read(2L).orElseThrow();
        reader2=readerService.read(3L).orElseThrow();

        Book book = bookService.read(1L).orElseThrow();
        Book book1 = bookService.read(2L).orElseThrow();
        Book book2 = bookService.read(3L).orElseThrow();
        Book book3 = bookService.read(4L).orElseThrow();

        logbookService.create(new Logbook(reader, book, new GregorianCalendar(2022, Calendar.MAY, 9),
                new GregorianCalendar(2022, Calendar.MAY, 23), true));

        logbookService.create(new Logbook(reader1, book1, new GregorianCalendar(2019, Calendar.AUGUST, 4),
                                                          new GregorianCalendar(2019, Calendar.SEPTEMBER, 8), true));

        logbookService.create(new Logbook(reader2, book2, new GregorianCalendar(2007, Calendar.APRIL, 9),
                                                          new GregorianCalendar(2007, Calendar.MAY, 13), true));

        logbookService.create(new Logbook(reader2, book3, new GregorianCalendar(2021, Calendar.DECEMBER, 21),
                                                          new GregorianCalendar(2022, Calendar.JANUARY, 30), true));
    }
}
