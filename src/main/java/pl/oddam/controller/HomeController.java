package pl.oddam.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.oddam.model.CurrentUser;
import pl.oddam.repository.GiftRepository;
import pl.oddam.service.LoginUserRoleCheckService;

@Controller
@RequestMapping("")
public class HomeController {
    private final GiftRepository giftRepository;
    private final LoginUserRoleCheckService loginUserRoleCheckService;

    public HomeController(GiftRepository giftRepository, LoginUserRoleCheckService loginUserRoleCheckService) {
        this.giftRepository = giftRepository;
        this.loginUserRoleCheckService = loginUserRoleCheckService;
    }

    @GetMapping("")
    public String home(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        int bagsCount = giftRepository.countBags();
        int supportedOrganizations = giftRepository.countSupportedOrganizations();
        int giftsCount = giftRepository.countAll();
        boolean isLogged = loginUserRoleCheckService.isLogged(customUser);
        if (isLogged) {
            model.addAttribute("user", customUser.getUser());
            model.addAttribute("isAdmin", loginUserRoleCheckService.isAdmin(customUser));
        }
        model.addAttribute("isLogged", isLogged);
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
