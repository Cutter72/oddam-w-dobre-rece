package pl.oddam.service;

import pl.oddam.model.User;

public interface UserService {
    User findByEmail(String name);
    void saveUser(User user);
    void saveAdmin(User user);
    void editAdmin(User user);
    void editUser(User user);
    void activateNewUser(String email);
    void banUser(Long id);
    void unBanUser(Long id);
}
