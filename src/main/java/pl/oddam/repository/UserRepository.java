package pl.oddam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oddam.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
