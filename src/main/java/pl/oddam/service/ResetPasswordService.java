package pl.oddam.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.oddam.model.DomainSettings;
import pl.oddam.model.ResetPassword;
import pl.oddam.repository.ResetPasswordRepository;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ResetPasswordService {
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ResetPasswordRepository resetPasswordRepository;
    private final DomainSettings domainSettings;

    public ResetPasswordService(EmailService emailService, BCryptPasswordEncoder passwordEncoder, ResetPasswordRepository resetPasswordRepository, DomainSettings domainSettings) {
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.resetPasswordRepository = resetPasswordRepository;
        this.domainSettings = domainSettings;
    }

    public void sendToken(String email) throws MessagingException {
        String token = passwordEncoder.encode(LocalDateTime.now().toString());
        String processedToken = token.replaceAll("[/?&=]", "");
        String text = "Kliknij w link aby zresetowac swoje hasło: <a href=\""+domainSettings.getAddress()+"token/" + processedToken + "\">link</a><br/>UWAGA! Ważność linku to 30 minut!";
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
            String email = resetPassword.getEmail();
            if (new Timestamp(System.currentTimeMillis()).getTime() - resetPassword.getResetStartTime() < domainSettings.getTokenTimeoutMillis()) {
                resetPasswordRepository.deleteAllByEmail(email);
                return email;
            } else {
                return "";
            }
        } catch (NullPointerException ex) {
            return "";
        }
    }
}
