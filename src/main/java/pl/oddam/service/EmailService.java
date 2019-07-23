package pl.oddam.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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

    public void sendMimeMessage(String from, String to, String subject, String text) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        try {
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(text,true);
            emailSender.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
