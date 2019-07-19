package pl.oddam.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
/*
Class copied from:
https://www.baeldung.com/spring-security-registration-captcha
 */
@Component
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "success",
        "challenge_ts",
        "hostname",
        "error-codes"
})
public class GoogleResponse {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("challenge_ts")
    private String challengeTs;

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("error-codes")
    private ErrorCode[] errorCodes;

    enum ErrorCode {
        MissingSecret,     InvalidSecret,
        MissingResponse,   InvalidResponse;

        private static Map<String, ErrorCode> errorsMap = new HashMap<>(4);

        static {
            errorsMap.put("missing-input-secret",   MissingSecret);
            errorsMap.put("invalid-input-secret",   InvalidSecret);
            errorsMap.put("missing-input-response", MissingResponse);
            errorsMap.put("invalid-input-response", InvalidResponse);
        }
    }
}
