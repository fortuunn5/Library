package com.example.Library.config;

import com.example.Library.model.Book;
import com.example.Library.model.Logbook;
import com.example.Library.model.Reader;
import com.example.Library.service.interfaces.BookService;
import com.example.Library.service.interfaces.LogbookService;
import com.example.Library.service.interfaces.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Configuration
@RequiredArgsConstructor
public class InitDB {

    private final ReaderService readerService;
    private final BookService bookService;
    private final PasswordEncoder passwordEncoder;
    @Bean
    public void createReader() {
        Reader reader0;
        Reader reader1;
        Reader reader2;

        readerService.create(Reader.builder()
                .fio("Петров Петр Петрович")
                .email("aaaaa@a.com")
                .username("aaaa")
                .password(passwordEncoder.encode("aaaa"))
                .role("ROLE_ADMIN")
                .build()
        );
        readerService.create(Reader.builder()
                .fio("Макарова Анастасия Александровна")
                .email("bbbbb@b.com")
                .username("bbbb")
                .password(passwordEncoder.encode("bbbb"))
                .build()
        );
        readerService.create(Reader.builder()
                .fio("Александров Леонид Эмирович")
                .email("ccccc@c.com")
                .username("cccc")
                .password(passwordEncoder.encode("cccc"))
                .build()
        );
        bookService.create(new Book(
                "Война и мир",
                "Лев Толстой",
                1873
        ));
        bookService.create(new Book(
                "Евгений Онегин",
                "Александр Пушкин",
                1823
        ));
        bookService.create(new Book(
                "Лолита",
                "Владимир Набоков",
                1955
        ));
        bookService.create(new Book(
                "Мастер и Маргарита",
                "Михаил Булгаков",
                1929
        ));

        reader0=readerService.read(1L);
        reader1=readerService.read(2L);
        reader2=readerService.read(3L);



        Book book = bookService.read(1L);
        Book book1 = bookService.read(2L);
        Book book2 = bookService.read(3L);
        Book book3 = bookService.read(4L);

    }
}
