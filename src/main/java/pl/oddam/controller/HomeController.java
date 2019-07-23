package pl.oddam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.oddam.model.DomainSettings;
import pl.oddam.service.ResetPasswordService;

@Controller
@RequestMapping("")
public class HomeController {
    private final ResetPasswordService resetPasswordService;
    private final DomainSettings domainSettings;

    public HomeController(ResetPasswordService resetPasswordService, DomainSettings domainSettings) {
        this.resetPasswordService = resetPasswordService;
        this.domainSettings = domainSettings;
    }

    @GetMapping("")
    public String home() {
        return "home";
    }

    @GetMapping("/test")
    public String testingViews() {
        return "user/formSuccess";
    }

    @GetMapping("/token/{token}")
    public String resetPassword(@PathVariable String token, Model model) {
        String email = resetPasswordService.getEmail(token);
        if (!email.equals("")) {
            model.addAttribute("reCaptchaKey", domainSettings.getSiteKey());
            model.addAttribute("email", email);
            return "newPassword";
        } else {
            return "wrongToken";
        }
    }
}
