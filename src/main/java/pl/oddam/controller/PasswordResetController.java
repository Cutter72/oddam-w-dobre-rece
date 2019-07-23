package pl.oddam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.oddam.model.ReCaptchaKeys;
import pl.oddam.service.ReCaptchaService;
import pl.oddam.service.ResetPasswordService;
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
    private final ReCaptchaKeys reCaptchaKeys;
    private final ResetPasswordService resetPasswordService;

    public PasswordResetController(UserServiceImpl userServiceImpl, ReCaptchaService reCaptchaService, ReCaptchaKeys reCaptchaKeys, ResetPasswordService resetPasswordService) {
        this.userServiceImpl = userServiceImpl;
        this.reCaptchaService = reCaptchaService;
        this.reCaptchaKeys = reCaptchaKeys;
        this.resetPasswordService = resetPasswordService;
    }

    @GetMapping("")
    public String sendTokenForm(Model model) {
        model.addAttribute("reCaptchaKey", reCaptchaKeys.getSiteKey());
        return "resetPassword";
    }

    @PostMapping("")
    public String sendTokenPost(@RequestParam("g-recaptcha-response") String recaptchaResponse, @RequestParam String email, Model model) throws IOException, MessagingException {
        if (reCaptchaService.processResponse(recaptchaResponse)) {
            try {
                userServiceImpl.findByEmail(email);
                resetPasswordService.sendToken(email);
                return "resetPasswordSuccess";
            } catch (NullPointerException e) {
                model.addAttribute("noSuchEmail", "Nie posiadamy takiego ("+email+") adresu e-mail w naszej bazie!");
                model.addAttribute("reCaptchaKey", reCaptchaKeys.getSiteKey());
                return "resetPassword";
            }
        } else {
            model.addAttribute("captchaNotChecked","Proszę zaznaczyć że nie jesteś robotem!");
            model.addAttribute("reCaptchaKey", reCaptchaKeys.getSiteKey());
            model.addAttribute("email", email);
            return "register";
        }
    }

    @PostMapping("/new")
    public String setNewPassword(@RequestParam("g-recaptcha-response") String recaptchaResponse, Model model, @RequestParam String password1) throws IOException {
        if (reCaptchaService.processResponse(recaptchaResponse)) {
            return "home";
        } else {
            model.addAttribute("captchaNotChecked","Proszę zaznaczyć że nie jesteś robotem!");
            model.addAttribute("reCaptchaKey", reCaptchaKeys.getSiteKey());
            return "resetPassword";
        }
    }
}
