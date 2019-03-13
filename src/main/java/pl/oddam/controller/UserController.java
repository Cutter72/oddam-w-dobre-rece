package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.oddam.model.CurrentUser;

@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("")
    public String user(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        model.addAttribute("user", customUser.getUser());
        return "user";
    }
    @GetMapping("/")
    public String userSlash() {
        return "redirect:/user";
    }

    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        model.addAttribute("user", customUser.getUser());
        return "user/profile";
    }
    @GetMapping("/profile/")
    public String userProfileSlash() {
        return "redirect:/user/profile";
    }
    @GetMapping("/settings")
    public String userSettings(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        model.addAttribute("user", customUser.getUser());
        return "user/settings";
    }
    @GetMapping("/settings/")
    public String userSettingsSlash() {
        return "redirect:/user/settings";
    }
}
