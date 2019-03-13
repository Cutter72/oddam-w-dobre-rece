package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.oddam.model.CurrentUser;

@Controller
@RequestMapping("/user/settings")
public class SettingsController {
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
}
