package pl.oddam.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.oddam.model.ResetPassword;
import pl.oddam.repository.ResetPasswordRepository;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class ResetPasswordService {
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ResetPasswordRepository resetPasswordRepository;

    public ResetPasswordService(EmailService emailService, BCryptPasswordEncoder passwordEncoder, ResetPasswordRepository resetPasswordRepository) {
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.resetPasswordRepository = resetPasswordRepository;
    }

    public void sendToken(String email) throws MessagingException {
        String token = passwordEncoder.encode(LocalDateTime.now().toString());
        String processedToken = token.replaceAll("[/?&=]", "");
        String text = "Kliknij w link aby zresetowac swoje hasło: <a href=\"http://localhost:8080/token/" + processedToken + "\">link</a>";
        emailService.sendMimeMessage("oddam.w.dobre.rece@interia.pl", email, "Reset hasła w portalu Oddam w dobre ręce", text);
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setEmail(email);
        resetPassword.setToken(processedToken);
        resetPassword.setResetStartTime(new Timestamp(System.currentTimeMillis()).getTime());
        resetPasswordRepository.save(resetPassword);
    }

    public String getEmail(String token) {
        try {
            ResetPassword resetPassword = resetPasswordRepository.findByToken(token);
            if (new Timestamp(System.currentTimeMillis()).getTime() - resetPassword.getResetStartTime() < 60000) {
                resetPasswordRepository.delete(resetPassword);
                return resetPassword.getEmail();
            } else {
                return "";
            }
        } catch (NullPointerException ex) {
            return "";
        }
    }
}
