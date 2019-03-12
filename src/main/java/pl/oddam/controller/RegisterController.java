package pl.oddam.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.oddam.model.User;
import pl.oddam.service.UserServiceImpl;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@Transactional
@RequestMapping("/register")
public class RegisterController {
    private final UserServiceImpl userServiceImpl;

    public RegisterController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/")
    public String registerFormSlash(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("")
    public String register(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        String existingEmail = null;
        System.out.println(existingEmail);
        try {
            existingEmail = userServiceImpl.findByEmail(user.getEmail()).getEmail();
        } catch (NullPointerException e) {
            userServiceImpl.saveUser(user);
        } finally {
            if (existingEmail != null) {
                model.addAttribute("duplicateEmail", "Email " + existingEmail + " jest już zajęty!");
                return "register";
            }
        }
        return "redirect:/";
    }
}
