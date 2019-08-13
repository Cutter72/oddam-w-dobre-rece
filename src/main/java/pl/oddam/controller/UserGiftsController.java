package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.oddam.model.CurrentUser;
import pl.oddam.model.Gift;
import pl.oddam.model.User;
import pl.oddam.repository.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/user/gifts")
public class UserGiftsController {
    private final GiftRepository giftRepository;

    public UserGiftsController(GiftRepository giftRepository) {
        this.giftRepository = giftRepository;
    }

    @GetMapping("")
    public String userGifts(@AuthenticationPrincipal CurrentUser customUser, Model model, @RequestParam(defaultValue = "none") String sortBy, @RequestParam(defaultValue = "default") String sortOrder, HttpSession sess) {
        User user = customUser.getUser();
        model.addAttribute("user", user);
        List<Gift> giftList = giftRepository.findAllByUser(user);
        if (sortBy.equals("created") && sortOrder.equals("asc")) {
            giftList.sort(Comparator.nullsFirst(Comparator.comparing(Gift::getCreated)));
        } else if (sortBy.equals("created") && sortOrder.equals("des")) {
            giftList.sort(Comparator.nullsFirst(Comparator.comparing(Gift::getCreated)).reversed());
        } else if (sortBy.equals("dateCollected") && sortOrder.equals("asc")) {
            giftList.sort(Comparator.nullsFirst(Comparator.comparing(Gift::getDateCollected, Comparator.nullsFirst(Comparator.naturalOrder()))));
        } else if (sortBy.equals("dateCollected") && sortOrder.equals("des")) {
            giftList.sort(Comparator.nullsFirst(Comparator.comparing(Gift::getDateCollected, Comparator.nullsFirst(Comparator.naturalOrder()))).reversed());
        }
        model.addAttribute("giftList", giftList);
        if ((boolean)sess.getAttribute("isAdmin")) {
            model.addAttribute("adminPanel", "<li><a href=\"/admin\">Panel Admina</a></li>");
        }
        return "user/gifts";
    }

    @GetMapping("/")
    public String userGiftsSlash() {
        return "redirect:/user/gifts";
    }

    @PostMapping("")
    public String userGiftEdit(@RequestParam Long giftId, @RequestParam String date, @RequestParam(defaultValue = "user") String destination) {
        String[] dateStrings = date.split("-");
        int[] dateInts = new int[dateStrings.length];
        for (int i = 0 ; i < dateStrings.length ; i++) {
            dateInts[i] = Integer.parseInt(dateStrings[i]);
        }
        Gift gift = giftRepository.findById(giftId).get();
        gift.setDateCollected(LocalDate.of(dateInts[0], dateInts[1], dateInts[2]).plusDays(1)); //why databse save it one day back?
        gift.setCollected(true);
        giftRepository.save(gift);
        return "redirect:/"+destination+"/gifts";
    }

}
