package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
            model.addAttribute("newUser", user);
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
            model.addAttribute("newUser", user);
            model.addAttribute("duplicateEmail", "Email " + existingEmail + " jest już zajęty!");
            return "admin";
        }
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String adminEdit(@AuthenticationPrincipal CurrentUser customUser, Model model, @PathVariable Long id) {
        model.addAttribute("user", customUser.getUser());
        model.addAttribute("userToEdit", userRepository.findById(id).get());
        Role userRole = roleRepository.findByName("ROLE_ADMIN");
        model.addAttribute("adminList", userRepository.findAllByRoles(new HashSet<Role>(Arrays.asList(userRole))));
        return "admin/adminEdit";
    }

    @PostMapping("/edit/{id}")
    public String adminEditPost(@ModelAttribute("userToEdit")@Valid User user, BindingResult result, Model model,
                                @AuthenticationPrincipal CurrentUser customUser, ModelMap modelMap, @PathVariable Long id) {
        User userToUpdate = userRepository.findById(id).get();
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        if (result.hasErrors()) {
            adminEdit(customUser, model, id);
            modelMap.put(BindingResult.class.getName() + ".userToEdit", result);
            return "admin/adminEdit";
        }
        String existingEmail;
        User duplicateUser;
        try {
            duplicateUser = userServiceImpl.findByEmail(user.getEmail());
        } catch (NullPointerException e) {
            userServiceImpl.editAdmin(userToUpdate);
            return "redirect:/admin";
        }
        if (duplicateUser != null) {
            existingEmail = duplicateUser.getEmail();
            if (duplicateUser.getId() == id) {
                userServiceImpl.editAdmin(userToUpdate);
                return "redirect:/admin";
            } else {
                adminEdit(customUser, model, id);
                model.addAttribute("duplicateEmail", "Email " + existingEmail + " jest już zajęty!");
                return "admin/adminEdit";
            }
        } else {
            userServiceImpl.editAdmin(userToUpdate);
            return "redirect:/admin";
        }
    }

    @GetMapping("/delete/{id}")
    public String adminDelete(@PathVariable Long id) {
        userRepository.delete(userRepository.findById(id).get());
        return "redirect:/admin";
    }

}
