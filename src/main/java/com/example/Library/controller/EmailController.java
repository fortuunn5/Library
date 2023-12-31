package com.example.Library.controller;

import com.example.Library.model.Logbook;
import com.example.Library.service.impl.EmailService;
import com.example.Library.service.interfaces.LogbookService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;
    private final LogbookService logbookService;

    public ResponseEntity<?> sendEmail(@PathParam("email") String email, @PathParam("subject") String subject, @PathParam("message") String message) {
        if(!email.isEmpty() && !subject.isEmpty() && !message.isEmpty()) {
            emailService.sendEmail(email, subject, message);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<?> sendEmailToAll() {
        List<Logbook> logbooks = logbookService.readAll();
        String subject = "Просрочка";
        String message = "";

        for (Logbook logbook : logbooks) {
            long dif;
            if (logbook.getDeliveryDate() != null) {
                dif = logbookService.getOverdueDays(logbook.getIssueDate(), logbook.getDeliveryDate());
            } else {
                dif = logbookService.getOverdueDays(logbook.getIssueDate(), Calendar.getInstance());
            }
            if (dif != 0) {
                float penalties = logbookService.getPenalties(dif);
                message = "Вы просрочили сдачу книги на " + dif + " дней. Задолженность составляет " + penalties + " рублей.";
            }
            sendEmail(logbook.getReader().getEmail(), subject, message);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
