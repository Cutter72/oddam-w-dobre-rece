package pl.oddam.service;

import pl.oddam.model.User;

public interface UserService {
    User findByUserName(String name);
    void saveUser(User user);
}
