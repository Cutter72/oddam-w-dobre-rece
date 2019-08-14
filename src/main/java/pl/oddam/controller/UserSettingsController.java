package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.oddam.model.CurrentUser;
import pl.oddam.model.User;
import pl.oddam.repository.UserRepository;
import pl.oddam.service.LoginUserRoleCheckService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/settings")
public class UserSettingsController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoginUserRoleCheckService loginUserRoleCheckService;

    public UserSettingsController(UserRepository userRepository, PasswordEncoder passwordEncoder, LoginUserRoleCheckService loginUserRoleCheckService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loginUserRoleCheckService = loginUserRoleCheckService;
    }

    @GetMapping("")
    public String userSettings(@AuthenticationPrincipal CurrentUser customUser, Model model, HttpSession sess) {
        model.addAttribute("user", customUser.getUser());
        model.addAttribute("isAdmin", loginUserRoleCheckService.isAdmin(customUser));
        return "user/settings";
    }

    @GetMapping("/")
    public String userSettingsSlash() {
        return "redirect:/user/settings";
    }

    @GetMapping("/edit-personal-data")
    public String editPersonalData(@AuthenticationPrincipal CurrentUser customUser, Model model, HttpSession sess) {
        model.addAttribute("user", customUser.getUser());
        model.addAttribute("isAdmin", loginUserRoleCheckService.isAdmin(customUser));
        return "user/editPersonalData";
    }
    @PostMapping("/edit-personal-data")
    public String editPersonalDataSaveToDb(@AuthenticationPrincipal CurrentUser customUser, @RequestParam String firstName, @RequestParam String lastName) {
        customUser.getUser().setFirstName(firstName);
        customUser.getUser().setLastName(lastName);
        userRepository.save(customUser.getUser());
        return "redirect:/user/profile";
    }
    @GetMapping("/edit-password")
    public String editPassword(@AuthenticationPrincipal CurrentUser customUser, Model model, HttpSession sess) {
        model.addAttribute("user", customUser.getUser());
        model.addAttribute("isAdmin", loginUserRoleCheckService.isAdmin(customUser));
        return "user/editPassword";
    }

    @PostMapping("/edit-password")
    public String editPasswordSaveToDb(@AuthenticationPrincipal CurrentUser customUser, @RequestParam String oldPassword, @RequestParam String newPassword, Model model, HttpSession sess) {
        User user = customUser.getUser();
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            model.addAttribute("success", "Hasło zmienione!");
        }
        model.addAttribute("user", customUser.getUser());
        model.addAttribute("isAdmin", loginUserRoleCheckService.isAdmin(customUser));
        return "user/editPassword";
    }
}
