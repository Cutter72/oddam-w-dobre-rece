package pl.oddam.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("domain")
@Data
public class DomainSettings {
    private String siteKey;
    private String secretKey;
    private String address;
    private String mail;
    private Long registerTimeoutMillis;
    private Long passwordResetTimeoutMillis;
}
