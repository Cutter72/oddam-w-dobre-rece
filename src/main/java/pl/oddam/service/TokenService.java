package pl.oddam.service;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.oddam.model.TokenParams;
import pl.oddam.repository.TokenParamsRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Transactional
public class TokenService {
    private final TokenParamsRepository tokenParamsRepository;

    public TokenService(TokenParamsRepository tokenParamsRepository) {
        this.tokenParamsRepository = tokenParamsRepository;
    }

    public String getEmail(String token) {
        if (tokenExist(token)) {
            return tokenParamsRepository.findByToken(token).getEmail();
        } else {
            return "";
        }
    }

    public TokenParams createNewToken(String email) {
        TokenParams newToken = new TokenParams();
        newToken.setEmail(email);
        newToken.setCreationTimeStamp(new Timestamp(System.currentTimeMillis()).getTime());
        newToken.setToken(generate(email));
        tokenParamsRepository.save(newToken);
        return newToken;
    }

    public String generate(String email) {
        ObjectIdGenerators.UUIDGenerator uuidGenerator = new ObjectIdGenerators.UUIDGenerator();
        return uuidGenerator.generateId(LocalDateTime.now() + email).toString();
    }

    public boolean tokenExist(String token) {
        try {
            tokenParamsRepository.findByToken(token);
            return true;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public boolean checkValidity(String token, long expectedTimeout) {
        if (tokenExist(token)) {
            TokenParams tokenToCheck = tokenParamsRepository.findByToken(token);
            return (System.currentTimeMillis() - tokenToCheck.getCreationTimeStamp()) < expectedTimeout;
        }
        return false;
    }

    public void deleteAllByEmail(String email) {
        tokenParamsRepository.deleteAllByEmail(email);
    }
}
