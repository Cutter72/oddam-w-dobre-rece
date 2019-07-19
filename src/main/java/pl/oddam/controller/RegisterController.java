package pl.oddam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.oddam.model.User;
import pl.oddam.service.ReCaptchaService;
import pl.oddam.service.UserServiceImpl;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@Transactional
@RequestMapping("/register")
public class RegisterController {
    private final UserServiceImpl userServiceImpl;
    private final ReCaptchaService reCaptchaService;

    public RegisterController(UserServiceImpl userServiceImpl, ReCaptchaService reCaptchaService) {
        this.userServiceImpl = userServiceImpl;
        this.reCaptchaService = reCaptchaService;
    }

    @GetMapping("")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/")
    public String registerFormSlash() {
        return "redirect:/register";
    }

    @PostMapping("")
    public String register(@RequestParam("g-recaptcha-response") String recaptchaResponse, @Valid User user, BindingResult result, Model model) throws IOException {
        if (reCaptchaService.processResponse(recaptchaResponse)) {
            if (result.hasErrors()) {
                return "register";
            }
            String existingEmail = null;
            try {
                existingEmail = userServiceImpl.findByEmail(user.getEmail()).getEmail();
            } catch (NullPointerException e) {
                userServiceImpl.saveUser(user);
            } finally {
                if (existingEmail != null) {
                    model.addAttribute("duplicateEmail", "Email " + existingEmail + " jest już zajęty!");
                    return "register";
                }
            }
            model.addAttribute("registerSuccess","Konto pomyślnie zarejestrowane! Możesz się teraz zalogować.");
            return "login";
        } else {
            model.addAttribute("captchaNotChecked","Proszę zaznaczyć że nie jesteś robotem!");
            return "register";
        }
    }
}
