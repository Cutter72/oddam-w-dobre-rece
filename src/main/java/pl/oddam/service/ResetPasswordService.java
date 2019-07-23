package pl.oddam.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

@Service
public class ResetPasswordService {
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    public ResetPasswordService(EmailService emailService, BCryptPasswordEncoder passwordEncoder) {
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public void sendToken(String email) throws MessagingException {
        String token = passwordEncoder.encode(LocalDateTime.now().toString());
        System.out.println(token);
        String processedToken = token.replaceAll("[/?&=]", "");
        System.out.println(processedToken);
        String text = "Kliknij w link aby zresetowac swoje hasło: <a href=\"http://localhost:8080/"+processedToken+"\">link</a>";
        emailService.sendMimeMessage("oddam.w.dobre.rece@interia.pl",email,"Reset hasła w portalu Oddam w dobre ręce",text);
    }

    public boolean checkValidity() {

        return false;
    }
}
