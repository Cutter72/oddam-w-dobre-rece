package pl.oddam.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.oddam.model.DomainSettings;
import pl.oddam.model.TokenParams;

import javax.mail.MessagingException;

@Service
@Transactional
public class RegisterService {
    private final EmailService emailService;
    private final DomainSettings domainSettings;
    private final TokenService tokenService;

    public RegisterService(EmailService emailService, DomainSettings domainSettings, TokenService tokenService) {
        this.emailService = emailService;
        this.domainSettings = domainSettings;
        this.tokenService = tokenService;
    }

    public void sendToken(String email) throws MessagingException {
        TokenParams newToken = tokenService.createNewToken(email);
        String text = "Kliknij w poniższy link aby aktywowac swoje konto:<br/><a href=\""+domainSettings.getAddress()+"register/" + newToken.getToken() + "\">link</a><br/>UWAGA! Ważność linku to 24 godziny!";
        emailService.sendMimeMessage(domainSettings.getMail(),email,"Aktywuj swoje konto na portalu Oddam w dobre ręce.", text);
    }
}
