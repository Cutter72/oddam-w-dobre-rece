package pl.oddam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @GetMapping("")
    public String register() {
        return "register";
    }
    @GetMapping("/")
    public String registerSlash() {
        return "register";
    }
}
