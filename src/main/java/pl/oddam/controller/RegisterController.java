package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.oddam.model.CurrentUser;
import pl.oddam.model.DomainSettings;
import pl.oddam.model.User;
import pl.oddam.service.*;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@Transactional
@RequestMapping("/register")
public class RegisterController {
    private final UserServiceImpl userServiceImpl;
    private final ReCaptchaService reCaptchaService;
    private final DomainSettings domainSettings;
    private final RegisterService registerService;
    private final TokenService tokenService;
    private final LoginUserRoleCheckService loginUserRoleCheckService;

    public RegisterController(UserServiceImpl userServiceImpl, ReCaptchaService reCaptchaService, DomainSettings domainSettings, RegisterService registerService, TokenService tokenService, LoginUserRoleCheckService loginUserRoleCheckService) {
        this.userServiceImpl = userServiceImpl;
        this.reCaptchaService = reCaptchaService;
        this.domainSettings = domainSettings;
        this.registerService = registerService;
        this.tokenService = tokenService;
        this.loginUserRoleCheckService = loginUserRoleCheckService;
    }

    @GetMapping("")
    public String registerForm(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        if (loginUserRoleCheckService.isLogged(customUser)) {
            return "redirect:/home";
        }
        model.addAttribute("user", new User());
        model.addAttribute("reCaptchaKey", domainSettings.getSiteKey());
        return "register";
    }

    @GetMapping("/")
    public String registerFormSlash() {
        return "redirect:/register";
    }

    @PostMapping("")
    public String register(@RequestParam("g-recaptcha-response") String recaptchaResponse, @Valid User user, BindingResult result, Model model, RedirectAttributes ra) throws IOException, MessagingException {
        if (reCaptchaService.processResponse(recaptchaResponse)) {
            if (result.hasErrors()) {
                model.addAttribute("reCaptchaKey", domainSettings.getSiteKey());
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
                    model.addAttribute("reCaptchaKey", domainSettings.getSiteKey());
                    return "register";
                }
            }
            registerService.sendToken(user.getEmail());
            ra.addFlashAttribute("registerSuccess","Konto pomyślnie zarejestrowane! Na maila przesłaliśmy link aktywacyjny. Pamiętaj o sprawdzeniu folderu SPAM!");
            return "redirect:/login";
        } else {
            model.addAttribute("captchaNotChecked","Proszę zaznaczyć że nie jesteś robotem!");
            model.addAttribute("reCaptchaKey", domainSettings.getSiteKey());
            return "register";
        }
    }

    @GetMapping("/{token}")
    public String tokenCheck(@PathVariable String token, Model model, RedirectAttributes ra) {
        if (tokenService.checkValidity(token,domainSettings.getRegisterTimeoutMillis())) {
            userServiceImpl.activateNewUser(tokenService.getEmail(token));
            ra.addFlashAttribute("registerSuccess", "Konto pomyślnie aktywowane!");
            tokenService.deleteAllByEmail(tokenService.getEmail(token));
            return "redirect:/login";
        } else {
            model.addAttribute("accountActivationFailure", "Coś poszło nie tak!");
            return "wrongToken";
        }
    }
}
