package com.example.Library.controller;

import com.example.Library.model.Reader;
import com.example.Library.repository.ReaderRepository;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final ReaderRepository readerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> registration (@PathParam("username") String username,
                                           @PathParam("password") String password,
                                           @PathParam("fio") String fio,
                                           @PathParam("email") String email) {
        if (!username.isEmpty() && !username.isBlank()
                && !password.isEmpty() && !password.isBlank()
                && !fio.isEmpty() && !fio.isBlank()
                && !email.isEmpty() && !email.isBlank()
        ) {
            readerRepository.save(new Reader(fio, email, username, passwordEncoder.encode(password)));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
