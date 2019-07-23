package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.oddam.model.CurrentUser;
import pl.oddam.model.ReCaptchaKeys;
import pl.oddam.model.User;
import pl.oddam.service.ReCaptchaService;
import pl.oddam.service.UserServiceImpl;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@Transactional
@RequestMapping("/password")
public class PasswordController {
    private final UserServiceImpl userServiceImpl;
    private final ReCaptchaService reCaptchaService;
    private final ReCaptchaKeys reCaptchaKeys;

    public PasswordController(UserServiceImpl userServiceImpl, ReCaptchaService reCaptchaService, ReCaptchaKeys reCaptchaKeys) {
        this.userServiceImpl = userServiceImpl;
        this.reCaptchaService = reCaptchaService;
        this.reCaptchaKeys = reCaptchaKeys;
    }

    @GetMapping("/change")
    public String changePasswordForm(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        model.addAttribute("user", customUser.getUser());
        model.addAttribute("reCaptchaKey", reCaptchaKeys.getSiteKey());
        return "user/changePassword";
    }

    @GetMapping("/reset")
    public String changePasswordForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("reCaptchaKey", reCaptchaKeys.getSiteKey());
        return "resetPassword";
    }

    @GetMapping("/change/")
    public String changePasswordFormSlash() {
        return "redirect:/password/change";
    }

    @GetMapping("")
    public String passwordForm() {
        return "redirect:/password/change";
    }

    @GetMapping("/")
    public String passwordFormSlash() {
        return "redirect:/password/change";
    }

    @PostMapping("")
    public String register(@RequestParam("g-recaptcha-response") String recaptchaResponse, @Valid User user, BindingResult result, Model model) throws IOException {
        if (reCaptchaService.processResponse(recaptchaResponse)) {
            if (result.hasErrors()) {
                model.addAttribute("reCaptchaKey", reCaptchaKeys.getSiteKey());
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
                    model.addAttribute("reCaptchaKey", reCaptchaKeys.getSiteKey());
                    return "register";
                }
            }
            model.addAttribute("registerSuccess","Konto pomyślnie zarejestrowane! Możesz się teraz zalogować.");
            return "login";
        } else {
            model.addAttribute("captchaNotChecked","Proszę zaznaczyć że nie jesteś robotem!");
            model.addAttribute("reCaptchaKey", reCaptchaKeys.getSiteKey());
            return "register";
        }
    }
}
