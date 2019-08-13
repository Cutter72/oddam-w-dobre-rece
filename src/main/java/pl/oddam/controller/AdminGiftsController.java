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
import pl.oddam.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin/gifts")
public class AdminGiftsController {
    private final GiftRepository giftRepository;
    private final UserRepository userRepository;

    public AdminGiftsController(GiftRepository giftRepository, UserRepository userRepository) {
        this.giftRepository = giftRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String adminGifts(@AuthenticationPrincipal CurrentUser customUser, Model model, @RequestParam(defaultValue = "none") String sortBy, @RequestParam(defaultValue = "default") String sortOrder, @RequestParam(required = false) String email, HttpSession sess) {
        model.addAttribute("adminPanel", "<li><a href=\"/admin\">Panel Admina</a></li>");
        User user = customUser.getUser();
        model.addAttribute("user", user);
        List<Gift> giftList;
        if (email != null) {
            if (email.equals("all")) {
                sess.setAttribute("giftsManagementEmail", null);
                giftList = giftRepository.findAll();
            } else {
                sess.setAttribute("giftsManagementEmail", email);
                giftList = giftRepository.findAllByUser(userRepository.findByEmail(email));
            }
        } else {
            if (sess.getAttribute("giftsManagementEmail") != null) {
                giftList = giftRepository.findAllByUser(userRepository.findByEmail((String) sess.getAttribute("giftsManagementEmail")));
            } else {
                giftList = giftRepository.findAll();
            }
        }
        if (sortBy.equals("created") && sortOrder.equals("asc")) {
            giftList.sort(Comparator.nullsFirst(Comparator.comparing(Gift::getCreated)));
        } else if (sortBy.equals("created") && sortOrder.equals("des")) {
            giftList.sort(Comparator.nullsFirst(Comparator.comparing(Gift::getCreated)).reversed());
        } else if (sortBy.equals("dateCollected") && sortOrder.equals("asc")) {
            giftList.sort(Comparator.nullsFirst(Comparator.comparing(Gift::getDateCollected, Comparator.nullsFirst(Comparator.naturalOrder()))));
        } else if (sortBy.equals("dateCollected") && sortOrder.equals("des")) {
            giftList.sort(Comparator.nullsFirst(Comparator.comparing(Gift::getDateCollected, Comparator.nullsFirst(Comparator.naturalOrder()))).reversed());
        } else if (sortBy.equals("email") && sortOrder.equals("asc")) {
            giftList.sort(Comparator.nullsFirst(Comparator.comparing(Gift::getUserEmail, Comparator.nullsFirst(Comparator.naturalOrder()))));
        } else if (sortBy.equals("email") && sortOrder.equals("des")) {
            giftList.sort(Comparator.nullsFirst(Comparator.comparing(Gift::getUserEmail, Comparator.nullsFirst(Comparator.naturalOrder()))).reversed());
        }
        model.addAttribute("giftList", giftList);
        return "admin/giftsManagement";
    }

    @GetMapping("/")
    public String adminGiftsSlash() {
        return "redirect:/admin/gifts";
    }

}
