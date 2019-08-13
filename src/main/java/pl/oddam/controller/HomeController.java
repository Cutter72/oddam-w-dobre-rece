package pl.oddam.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.oddam.repository.GiftRepository;

@Controller
@RequestMapping("")
public class HomeController {
    private final GiftRepository giftRepository;

    public HomeController(GiftRepository giftRepository) {
        this.giftRepository = giftRepository;
    }

    @GetMapping("")
    public String home(Model model) {
        int bagsCount = giftRepository.countBags();
        int supportedOrganizations = giftRepository.countSupportedOrganizations();
        int giftsCount = giftRepository.countAll();
        model.addAttribute("bagsCount", bagsCount);
        model.addAttribute("supportedOrganizations", supportedOrganizations);
        model.addAttribute("giftsCount", giftsCount);
        return "home";
    }

    @GetMapping("/test")
    public String testingViews() {
        return "user/formSuccess";
    }
    @GetMapping("/account/deactivated")
    public String accountDeactivated(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Authentication authentication) {
        SecurityContextLogoutHandler contextLogoutHandler = new SecurityContextLogoutHandler();
        contextLogoutHandler.logout(request,response,authentication);
        return "accountDeactivated";
    }
}
