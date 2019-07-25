package pl.oddam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.oddam.model.DomainSettings;
import pl.oddam.model.User;
import pl.oddam.service.ReCaptchaService;
import pl.oddam.service.PasswordResetService;
import pl.oddam.service.TokenService;
import pl.oddam.service.UserServiceImpl;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;

@Controller
@Transactional
@RequestMapping("/password/reset")
public class PasswordResetController {
    private final UserServiceImpl userServiceImpl;
    private final ReCaptchaService reCaptchaService;
    private final DomainSettings domainSettings;
    private final PasswordResetService passwordResetService;
    private final TokenService tokenService;

    public PasswordResetController(UserServiceImpl userServiceImpl, ReCaptchaService reCaptchaService, DomainSettings domainSettings, PasswordResetService passwordResetService, TokenService tokenService) {
        this.userServiceImpl = userServiceImpl;
        this.reCaptchaService = reCaptchaService;
        this.domainSettings = domainSettings;
        this.passwordResetService = passwordResetService;
        this.tokenService = tokenService;
    }

    @GetMapping("")
    public String sendTokenForm(Model model) {
        model.addAttribute("reCaptchaKey", domainSettings.getSiteKey());
        return "resetPassword";
    }

    @PostMapping("")
    public String sendTokenPost(@RequestParam("g-recaptcha-response") String recaptchaResponse, @RequestParam String email, Model model) throws IOException, MessagingException {
        if (reCaptchaService.processResponse(recaptchaResponse)) {
            try {
                userServiceImpl.findByEmail(email);
                passwordResetService.sendToken(email);
                return "tokenSendSuccess";
            } catch (NullPointerException e) {
                model.addAttribute("noSuchEmail", "Nie posiadamy takiego ("+email+") adresu e-mail w naszej bazie!");
                model.addAttribute("reCaptchaKey", domainSettings.getSiteKey());
                return "resetPassword";
            }
        } else {
            model.addAttribute("captchaNotChecked","Proszę zaznaczyć że nie jesteś robotem!");
            model.addAttribute("reCaptchaKey", domainSettings.getSiteKey());
            model.addAttribute("email", email);
            return "resetPassword";
        }
    }

    @PostMapping("/new")
    public String setNewPassword(@RequestParam("g-recaptcha-response") String recaptchaResponse, Model model, @RequestParam String password1, @RequestParam String email) throws IOException {
        if (reCaptchaService.processResponse(recaptchaResponse)) {
            User user = userServiceImpl.findByEmail(email);
            user.setPassword(password1);
            userServiceImpl.saveUser(user);
            return "resetPasswordSuccess";
        } else {
            model.addAttribute("captchaNotChecked","Proszę zaznaczyć że nie jesteś robotem!");
            model.addAttribute("reCaptchaKey", domainSettings.getSiteKey());
            model.addAttribute("email", email);
            return "newPassword";
        }
    }

    @GetMapping("/{token}")
    public String tokenCheck(@PathVariable String token, Model model) {
        if (tokenService.checkValidity(token,1800000)) {
            String email = tokenService.getEmail(token);
            model.addAttribute("reCaptchaKey", domainSettings.getSiteKey());
            model.addAttribute("email", email);
            tokenService.deleteAllByEmail(email);
            return "newPassword";
        } else {
            return "wrongToken";
        }
    }
}
