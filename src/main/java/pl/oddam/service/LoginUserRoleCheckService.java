package pl.oddam.service;

import org.springframework.stereotype.Service;
import pl.oddam.model.CurrentUser;
import pl.oddam.model.Role;
import pl.oddam.model.User;
import pl.oddam.repository.RoleRepository;

@Service
public class LoginUserRoleCheckService {
    private final RoleRepository roleRepository;

    public LoginUserRoleCheckService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public boolean isLogged(CurrentUser customUser) {
        try {
            customUser.getUser();
            return true;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public boolean isAdmin(CurrentUser customUser) {
        try {
            User user = customUser.getUser();
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            if (user.getRoles().contains(adminRole)) {
                return true;
            }
        } catch (NullPointerException ex) {}
        return false;
    }

    public String roleCheck(CurrentUser customUser) {
        try {
            User user = customUser.getUser();
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            Role userRole = roleRepository.findByName("ROLE_USER");
            if (user.getRoles().contains(adminRole)) {
                return "admin";
            }
            if (user.getRoles().contains(userRole)) {
                return "user";
            }
        } catch (NullPointerException ex) {}
        return "/account/deactivated";
    }
}
