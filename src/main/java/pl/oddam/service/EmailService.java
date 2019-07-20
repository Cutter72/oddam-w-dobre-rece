package pl.oddam.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String from, String to, String subject, String text) {
        try {
            SimpleMailMessage emailToSend = new SimpleMailMessage();
            emailToSend.setTo(to);
            emailToSend.setFrom(from);
            emailToSend.setSubject(subject);
            emailToSend.setText(text);
            emailSender.send(emailToSend);
        } catch (MailException ex) {
            ex.printStackTrace();
        }
    }
}
