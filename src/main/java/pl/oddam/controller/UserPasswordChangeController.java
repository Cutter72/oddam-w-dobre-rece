package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.oddam.model.CurrentUser;
import pl.oddam.model.DomainSettings;
import pl.oddam.model.User;
import pl.oddam.repository.UserRepository;
import pl.oddam.service.LoginUserRoleCheckService;
import pl.oddam.service.ReCaptchaService;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;

@Controller
@Transactional
@RequestMapping("user/password")
public class UserPasswordChangeController {
    private final UserRepository userRepository;
    private final ReCaptchaService reCaptchaService;
    private final DomainSettings domainSettings;
    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginUserRoleCheckService loginUserRoleCheckService;

    public UserPasswordChangeController(UserRepository userRepository, ReCaptchaService reCaptchaService, DomainSettings domainSettings, BCryptPasswordEncoder passwordEncoder, LoginUserRoleCheckService loginUserRoleCheckService) {
        this.userRepository = userRepository;
        this.reCaptchaService = reCaptchaService;
        this.domainSettings = domainSettings;
        this.passwordEncoder = passwordEncoder;
        this.loginUserRoleCheckService = loginUserRoleCheckService;
    }



    @GetMapping("/change")
    public String changePasswordForm(@AuthenticationPrincipal CurrentUser customUser, Model model, HttpSession sess) {
        model.addAttribute("user", customUser.getUser());
        model.addAttribute("reCaptchaKey", domainSettings.getSiteKey());
        model.addAttribute("isAdmin", loginUserRoleCheckService.isAdmin(customUser));
        return "user/changePassword";
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

    @PostMapping("/change")
    public String register(@RequestParam("g-recaptcha-response") String recaptchaResponse, @AuthenticationPrincipal CurrentUser customUser, Model model, @RequestParam String oldPassword, @RequestParam String password1, HttpSession sess) throws IOException {
        model.addAttribute("isAdmin", loginUserRoleCheckService.isAdmin(customUser));
        if (reCaptchaService.processResponse(recaptchaResponse)) {
            User user = customUser.getUser();
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(password1));
                userRepository.save(user);
                return "user/passwordChangeSuccess";
            } else {
                model.addAttribute("user", customUser.getUser());
                model.addAttribute("reCaptchaKey", domainSettings.getSiteKey());
                model.addAttribute("wrongPassword", "Niepoprawne hasło!");
                return "user/changePassword";
            }
        } else {
            model.addAttribute("user", customUser.getUser());
            model.addAttribute("captchaNotChecked","Proszę zaznaczyć że nie jesteś robotem!");
            model.addAttribute("reCaptchaKey", domainSettings.getSiteKey());
            return "user/changePassword";
        }
    }
}
