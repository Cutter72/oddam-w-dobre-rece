package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.oddam.model.CurrentUser;
import pl.oddam.model.Gift;
import pl.oddam.model.User;
import pl.oddam.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

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
    public String userGifts(@AuthenticationPrincipal CurrentUser customUser, Model model, @RequestParam(defaultValue = "none") String sortingBy, @RequestParam(defaultValue = "default") String sortingOrder) {
        User user = customUser.getUser();
        String destinationView;
        if (user.getRoles().contains(roleRepository.findByName("ROLE_ADMIN"))) {
            destinationView = "admin/gifts";
        } else {
            destinationView = "user/gifts";
        }
        model.addAttribute("user", user);
        List<Gift> giftList = giftRepository.findAllByUser(user);
        if (sortingBy.equals("created") && sortingOrder.equals("asc")) {
            giftList.sort(Comparator.nullsFirst(Comparator.comparing(Gift::getCreated)));
        } else if (sortingBy.equals("created") && sortingOrder.equals("des")) {
            giftList.sort(Comparator.nullsFirst(Comparator.comparing(Gift::getCreated)).reversed());
        } else if (sortingBy.equals("dateCollected") && sortingOrder.equals("asc")) {
            giftList.sort(Comparator.nullsFirst(Comparator.comparing(Gift::isCollected)));
        } else if (sortingBy.equals("dateCollected") && sortingOrder.equals("des")) {
            giftList.sort(Comparator.nullsFirst(Comparator.comparing(Gift::isCollected)).reversed());
        }
        model.addAttribute("giftList", giftList);
        return destinationView;
    }

    @GetMapping("/")
    public String userGiftsSlash() {
        return "redirect:/user/gifts";
    }

    @PostMapping("")
    public String userGiftEdit(@RequestParam Long giftId, @RequestParam String date) {
        String[] dateStrings = date.split("-");
        int[] dateInts = new int[dateStrings.length];
        for (int i = 0 ; i < dateStrings.length ; i++) {
            dateInts[i] = Integer.parseInt(dateStrings[i]);
        }
        Gift gift = giftRepository.findById(giftId).get();
        gift.setDateCollected(LocalDate.of(dateInts[0], dateInts[1], dateInts[2]).plusDays(1)); //why databse save it one day back?
        gift.setCollected(true);
        giftRepository.save(gift);
        return "redirect:/user/gifts";
    }

}
