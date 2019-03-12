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
        User entityUser;
        try{
            entityUser = customUser.getUser();
        }catch(NullPointerException e){
            return "redirect:/login";
        }
        Role userRole = roleRepository.findByName("ROLE_ADMIN");
        if (entityUser.getRoles().equals(new HashSet<Role>(Arrays.asList(userRole)))) {
            return "redirect:/admin";
        }
        return "redirect:/user";
    }
}
