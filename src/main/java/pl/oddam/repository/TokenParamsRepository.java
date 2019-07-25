package pl.oddam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oddam.model.TokenParams;

@Repository
public interface TokenParamsRepository extends JpaRepository<TokenParams, Long> {
    TokenParams findByToken(String token);
    void deleteAllByEmail(String email);

}
