package pl.oddam.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {

    @GetMapping("")
    public String home() {
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
