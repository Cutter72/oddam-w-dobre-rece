package pl.oddam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.oddam.model.ReCaptchaKeys;
import pl.oddam.service.ResetPasswordService;

@Controller
@RequestMapping("")
public class HomeController {
    private final ResetPasswordService resetPasswordService;
    private final ReCaptchaKeys reCaptchaKeys;

    public HomeController(ResetPasswordService resetPasswordService, ReCaptchaKeys reCaptchaKeys) {
        this.resetPasswordService = resetPasswordService;
        this.reCaptchaKeys = reCaptchaKeys;
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
            model.addAttribute("reCaptchaKey", reCaptchaKeys.getSiteKey());
            model.addAttribute("email", email);
            return "newPassword";
        } else {
            return "wrongToken";
        }
    }
}
