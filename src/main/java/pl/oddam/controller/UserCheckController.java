package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.oddam.model.CurrentUser;
import pl.oddam.model.Role;
import pl.oddam.model.User;
import pl.oddam.repository.RoleRepository;
import pl.oddam.service.LoginUserRoleCheckService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/usercheck")
public class UserCheckController {
    private final RoleRepository roleRepository;
    private final LoginUserRoleCheckService loginUserRoleCheckService;

    public UserCheckController(RoleRepository roleRepository, LoginUserRoleCheckService loginUserRoleCheckService) {
        this.roleRepository = roleRepository;
        this.loginUserRoleCheckService = loginUserRoleCheckService;
    }

    @GetMapping("")
    public String admin(@AuthenticationPrincipal CurrentUser customUser, HttpSession sess) {
        if (loginUserRoleCheckService.isLogged(customUser)) {
            return "redirect:"+loginUserRoleCheckService.roleCheck(customUser);
        }
        return "redirect:/login";
    }
}
