package pl.oddam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping("")
    public String login() {
        return "login";
    }
    @GetMapping("/")
    public String loginSlash() {
        return "redirect:/login";
    }
    @PostMapping("")
    public String login(@RequestParam(value = "error", defaultValue = "false") boolean error, Model model) {
        if (error) {
            model.addAttribute("errorLogin", "Użytkownik lub hasło nieprawidłowe!");
        }
        return "login";
    }
}
