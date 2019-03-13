package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.oddam.model.CurrentUser;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("")
    public String admin(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        model.addAttribute("userName", customUser.getUser().getFirstName());
        return "admin";
    }

    @GetMapping("/")
    public String adminSlash() {
        return "redirect:/admin";
    }
}
