package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.oddam.model.CurrentUser;
import pl.oddam.model.User;
import pl.oddam.repository.*;

@Controller
@RequestMapping("/user/gifts")
public class UserGiftsController {
    private final GiftRepository giftRepository;
    private final RoleRepository roleRepository;

    public UserGiftsController(GiftRepository giftRepository, RoleRepository roleRepository) {
        this.giftRepository = giftRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("")
    public String userGifts(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        User user = customUser.getUser();
        String destinationView;
        if (user.getRoles().contains(roleRepository.findByName("ROLE_ADMIN"))) {
            destinationView = "admin/gifts";
        } else {
            destinationView = "user/gifts";
        }
        model.addAttribute("user", user);
        model.addAttribute("giftList", giftRepository.findAllByUser(user));
        return destinationView;
    }

    @GetMapping("/")
    public String userGiftsSlash() {
        return "redirect:/user/gifts";
    }

    @GetMapping("/edit/{id}")
    public String userGiftEdit() {
        return "/user/gifts";
    }

}
