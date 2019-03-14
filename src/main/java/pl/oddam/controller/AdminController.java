package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.oddam.model.CurrentUser;
import pl.oddam.model.Role;
import pl.oddam.model.User;
import pl.oddam.repository.RoleRepository;
import pl.oddam.repository.UserRepository;
import pl.oddam.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;
    private final UserServiceImpl userServiceImpl;
    private final RoleRepository roleRepository;

    public AdminController(UserRepository userRepository, UserServiceImpl userServiceImpl, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userServiceImpl = userServiceImpl;
        this.roleRepository = roleRepository;
    }

    @GetMapping("")
    public String admin(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        model.addAttribute("user", customUser.getUser());
        model.addAttribute("newUser", new User());
        Role userRole = roleRepository.findByName("ROLE_ADMIN");
        model.addAttribute("adminList", userRepository.findAllByRoles(new HashSet<Role>(Arrays.asList(userRole))));
        return "admin";
    }

    @GetMapping("/")
    public String adminSlash() {
        return "redirect:/admin";
    }

    @PostMapping("")
    public String adminSave(@ModelAttribute("newUser")@Valid User user, BindingResult result, Model model,
                            @AuthenticationPrincipal CurrentUser customUser, ModelMap modelMap) {
        if (result.hasErrors()) {
            admin(customUser, model);
            modelMap.put(BindingResult.class.getName() + ".newUser", result);
            return "admin";
        }
        String existingEmail = null;
        try {
            existingEmail = userServiceImpl.findByEmail(user.getEmail()).getEmail();
        } catch (NullPointerException e) {
            userServiceImpl.saveAdmin(user);
        }
        if (existingEmail != null) {
            admin(customUser, model);
            model.addAttribute("duplicateEmail", "Email " + existingEmail + " jest już zajęty!");
            return "admin";
        }
        return "redirect:/admin";
    }

}
