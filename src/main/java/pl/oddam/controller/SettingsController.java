package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.oddam.model.CurrentUser;
import pl.oddam.repository.UserRepository;

@Controller
@RequestMapping("/user/settings")
public class SettingsController {
    private final UserRepository userRepository;

    public SettingsController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String userSettings(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        model.addAttribute("user", customUser.getUser());
        return "user/settings";
    }
    @GetMapping("/")
    public String userSettingsSlash() {
        return "redirect:/user/settings";
    }
    @GetMapping("/edit-personal-data")
    public String editPersonalData(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        model.addAttribute("user", customUser.getUser());
        return "user/editPersonalData";
    }
    @PostMapping("/edit-personal-data")
    public String editPersonalDataSaveToDb(@AuthenticationPrincipal CurrentUser customUser, @RequestParam String firstName, @RequestParam String lastName) {
        customUser.getUser().setFirstName(firstName);
        customUser.getUser().setLastName(lastName);
        userRepository.save(customUser.getUser());
        return "redirect:/user/profile";
    }
}
