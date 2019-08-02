package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.oddam.model.CurrentUser;
import pl.oddam.model.Role;
import pl.oddam.model.User;
import pl.oddam.repository.RoleRepository;

import java.util.Arrays;
import java.util.HashSet;

@Controller
@RequestMapping("/usercheck")
public class UserCheckController {
    private final RoleRepository roleRepository;

    public UserCheckController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping("")
    public String admin(@AuthenticationPrincipal CurrentUser customUser) {
        User user;
        try {
            user = customUser.getUser();
        } catch (NullPointerException ex) {
            return "redirect:/login";
        }
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (user.getRoles().contains(adminRole)) {
            return "redirect:/admin";
        }
        if (user.getRoles().contains(userRole)) {
            return "redirect:/user";
        }
        return "redirect:/account/deactivated";
    }
}
