package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.oddam.model.CurrentUser;
import pl.oddam.model.Gift;
import pl.oddam.model.User;
import pl.oddam.repository.GiftRepository;
import pl.oddam.repository.RoleRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin/gifts")
public class AdminGiftsController {
    private final GiftRepository giftRepository;
    private final RoleRepository roleRepository;

    public AdminGiftsController(GiftRepository giftRepository, RoleRepository roleRepository) {
        this.giftRepository = giftRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("")
    public String adminGifts(@AuthenticationPrincipal CurrentUser customUser, Model model, @RequestParam(defaultValue = "none") String sortingBy, @RequestParam(defaultValue = "default") String sortingOrder) {
        model.addAttribute("adminPanel", "<li><a href=\"/admin\">Panel Admina</a></li>");
        return "admin/giftsManagement";
    }

    @GetMapping("/")
    public String adminGiftsSlash() {
        return "redirect:/admin/gifts";
    }

    @PostMapping("")
    public String adminGiftEdit(@RequestParam Long giftId, @RequestParam String date) {
        return "redirect:/admin/gifts";
    }

}
