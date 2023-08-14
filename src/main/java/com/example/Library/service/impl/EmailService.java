package com.example.Library.service.impl;

import com.example.Library.exception.NotFoundException;
import com.example.Library.model.Logbook;
import com.example.Library.service.interfaces.LogbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final LogbookService logbookService;

    private final JavaMailSender javaMailSender;

    public void sendEmail(String toAddress, String subject, String message) {
        if(toAddress.isEmpty() || subject.isEmpty() || message.isEmpty()){
            throw new NotFoundException();
        }


        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);
    }

    public void sendEmailToAll() {
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

    }
}
