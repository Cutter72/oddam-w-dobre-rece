package pl.oddam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oddam.model.Role;
import pl.oddam.model.User;

import java.util.HashSet;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String name);
    List<User> findAllByRoles(HashSet<Role> roles);
}
