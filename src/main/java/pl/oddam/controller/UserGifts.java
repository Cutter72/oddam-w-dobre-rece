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
import pl.oddam.model.Organization;
import pl.oddam.model.User;
import pl.oddam.model.dto.StepOneToThreeParameters;
import pl.oddam.repository.*;
import pl.oddam.service.OrganizationServiceImpl;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/user/gifts")
public class UserGifts {

    public UserGifts() {
    }

    @GetMapping("")
    public String userGifts(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        model.addAttribute("user", customUser.getUser());
        return "user/giftsEdit";
    }

    @GetMapping("/")
    public String userGiftsSlash() {
        return "redirect:/user/gifts";
    }

}
