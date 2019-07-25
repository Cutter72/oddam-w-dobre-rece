package pl.oddam.service;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenGenerator {
    public static String generate() {
        ObjectIdGenerators.UUIDGenerator uuidGenerator = new ObjectIdGenerators.UUIDGenerator();
        return uuidGenerator.generateId(LocalDateTime.now()).toString();
    }
}
