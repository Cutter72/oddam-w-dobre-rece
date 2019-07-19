package pl.oddam.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("google.recaptcha")
@Data
public class ReCaptchaKeys {
    private String siteKey;
    private String secretKey;
}
