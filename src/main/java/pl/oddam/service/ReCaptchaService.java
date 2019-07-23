package pl.oddam.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import pl.oddam.model.GoogleResponse;
import pl.oddam.model.DomainSettings;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ReCaptchaService {
    private DomainSettings domainSettings;

    public ReCaptchaService(DomainSettings domainSettings) {
        this.domainSettings = domainSettings;
    }

    public boolean processResponse(String recaptchaResponse) throws IOException {
        //create connection to google
        URL url = new URL("https://www.google.com/recaptcha/api/siteverify");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //request header
        conn.setRequestMethod("POST");
        //params string
        String parameters = "secret=" + domainSettings.getSecretKey() + "&response=" + recaptchaResponse;
        //send post request
        conn.setDoOutput(true);
        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        outStream.writeBytes(parameters);
        outStream.flush();
        outStream.close();
        //read response from google with Commmons IO
        String strGoogleResponse = IOUtils.toString(new InputStreamReader(conn.getInputStream()));
        //parse JSON response and return 'success' value
        ObjectMapper mapper = new ObjectMapper();
        GoogleResponse googleResponse = mapper.readValue(strGoogleResponse, GoogleResponse.class);
        return googleResponse.isSuccess();
    }
}
