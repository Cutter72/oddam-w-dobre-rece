package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.oddam.model.CurrentUser;
import pl.oddam.repository.*;

@Controller
@RequestMapping("/user/gifts")
public class UserGiftsController {
    private final GiftRepository giftRepository;

    public UserGiftsController(GiftRepository giftRepository) {
        this.giftRepository = giftRepository;
    }

    @GetMapping("")
    public String userGifts(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        model.addAttribute("user", customUser.getUser());
        model.addAttribute("giftList", giftRepository.findAll());
        return "user/giftsEdit";
    }

    @GetMapping("/")
    public String userGiftsSlash() {
        return "redirect:/user/gifts";
    }

}
