package com.example.Library.controller;

import com.example.Library.model.Logbook;
import com.example.Library.service.EmailService;
import com.example.Library.service.LogbookService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public ResponseEntity<?> sendEmailToAll() {
        List<Logbook> logbooks = logbookService.readAll();
        String subject = "Просрочка";
        String message = "";
        long endDate = 0;
        long startDate = 0;
        long dif = 0;
        long overdueDays = 0;
        float penalties = 0;
        double koef = 0.03;

        for(int i=0; i<logbooks.size(); i++) {
            endDate = logbooks.get(i).getDeliveryDate().getTimeInMillis();
            startDate = logbooks.get(i).getIssueDate().getTimeInMillis();
            dif = TimeUnit.MILLISECONDS.toDays(endDate-startDate);
            if(dif>30) {
                overdueDays = dif-30;
                penalties = (float) (overdueDays * koef);
                message = "Вы просрочили сдачу книги на " + overdueDays + " дней. Задолженность составляет " + penalties + " рублей.";
            }
            sendEmail(logbooks.get(i).getReader().getEmail(), subject, message);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
