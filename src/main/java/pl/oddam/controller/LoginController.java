package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.oddam.model.CurrentUser;
import pl.oddam.service.LoginUserRoleCheckService;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final LoginUserRoleCheckService loginUserRoleCheckService;

    public LoginController(LoginUserRoleCheckService loginUserRoleCheckService) {
        this.loginUserRoleCheckService = loginUserRoleCheckService;
    }

    @GetMapping("")
    public String login(@AuthenticationPrincipal CurrentUser customUser) {
        if (loginUserRoleCheckService.isLogged(customUser)) {
            return "redirect:/home";
        }
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
