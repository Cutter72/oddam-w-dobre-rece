package pl.oddam.controller;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
    private final UserRepository userRepository;
    private final UserServiceImpl userServiceImpl;
    private final RoleRepository roleRepository;

    public AdminUserController(UserRepository userRepository, UserServiceImpl userServiceImpl, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userServiceImpl = userServiceImpl;
        this.roleRepository = roleRepository;
    }

    @GetMapping("")
    public String adminUser(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        model.addAttribute("user", customUser.getUser());
        model.addAttribute("newUser", new User());
        Role userRole = roleRepository.findByName("ROLE_USER");
        model.addAttribute("userList", userRepository.findAllByRoles(new HashSet<Role>(Arrays.asList(userRole))));
        return "adminUser";
    }

    @GetMapping("/")
    public String adminUserSlash() {
        return "redirect:/admin/user";
    }

    @PostMapping("")
    public String adminUserSave(@ModelAttribute("newUser")@Valid User user, BindingResult result, Model model,
                            @AuthenticationPrincipal CurrentUser customUser, ModelMap modelMap) {
        if (result.hasErrors()) {
            adminUser(customUser, model);
            model.addAttribute("newUser", user);
            modelMap.put(BindingResult.class.getName() + ".newUser", result);
            return "adminUser";
        }
        String existingEmail = null;
        try {
            existingEmail = userServiceImpl.findByEmail(user.getEmail()).getEmail();
        } catch (NullPointerException e) {
            userServiceImpl.saveUser(user);
        }
        if (existingEmail != null) {
            adminUser(customUser, model);
            model.addAttribute("newUser", user);
            model.addAttribute("duplicateEmail", "Email " + existingEmail + " jest już zajęty!");
            return "adminUser";
        }
        return "redirect:/admin/user#list";
    }

    @GetMapping("/edit/{id}")
    public String adminUserEdit(@AuthenticationPrincipal CurrentUser customUser, Model model, @PathVariable Long id) {
        model.addAttribute("user", customUser.getUser());
        model.addAttribute("userToEdit", userRepository.findById(id).get());
        Role userRole = roleRepository.findByName("ROLE_USER");
        model.addAttribute("userList", userRepository.findAllByRoles(new HashSet<Role>(Arrays.asList(userRole))));
        return "admin/userEdit";
    }

    @PostMapping("/edit/{id}")
    public String adminUserEditPost(@ModelAttribute("userToEdit")@Valid User user, BindingResult result, Model model,
                                @AuthenticationPrincipal CurrentUser customUser, ModelMap modelMap, @PathVariable Long id) {
        User userToUpdate = userRepository.findById(id).get();
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        if (result.hasErrors()) {
            adminUserEdit(customUser, model, id);
            modelMap.put(BindingResult.class.getName() + ".userToEdit", result);
            return "admin/userEdit";
        }
        String existingEmail;
        User duplicateUser;
        try {
            duplicateUser = userServiceImpl.findByEmail(user.getEmail());
        } catch (NullPointerException e) {
            userServiceImpl.editUser(userToUpdate);
            return "redirect:/admin/user#list";
        }
        if (duplicateUser != null) {
            existingEmail = duplicateUser.getEmail();
            if (duplicateUser.getId() == id) {
                userServiceImpl.editUser(userToUpdate);
                return "redirect:/admin/user#list";
            } else {
                adminUserEdit(customUser, model, id);
                model.addAttribute("duplicateEmail", "Email " + existingEmail + " jest już zajęty!");
                return "admin/userEdit";
            }
        } else {
            userServiceImpl.editUser(userToUpdate);
            return "redirect:/admin/user#list";
        }
    }

    @GetMapping("/delete/{id}")
    public String adminUserDelete(@PathVariable Long id) {
        userRepository.delete(userRepository.findById(id).get());
        return "redirect:/admin/user#list";
    }
    @GetMapping("/disable/{id}")
    public String adminUserDisable(@PathVariable Long id) {
        User user = userRepository.findById(id).get();
        user.setEnabled(0);
        userRepository.save(user);
        return "redirect:/admin/user#list";
    }

    @GetMapping("/enable/{id}")
    public String adminUserEnable(@PathVariable Long id) {
        User user = userRepository.findById(id).get();
        user.setEnabled(1);
        userRepository.save(user);
        return "redirect:/admin/user#list";
    }

}
