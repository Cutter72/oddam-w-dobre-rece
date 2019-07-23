package pl.oddam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.oddam.service.ResetPasswordService;

@Controller
@RequestMapping("")
public class HomeController {
    private final ResetPasswordService resetPasswordService;

    public HomeController(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
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
    public String resetPassword(@PathVariable String token) {
        if (resetPasswordService.checkValidity(token)) {

            return "newPassword";
        } else {
            return "wrongToken";
        }
    }
}
