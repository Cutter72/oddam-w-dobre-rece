package pl.oddam.service;

import org.springframework.stereotype.Service;
import pl.oddam.model.ReCaptchaKeys;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ReCaptchaService {
    private ReCaptchaKeys reCaptchaKeys;

    public ReCaptchaService(ReCaptchaKeys reCaptchaKeys) {
        this.reCaptchaKeys = reCaptchaKeys;
    }

    public boolean processResponse(String recaptchaResponse) throws IOException {
        //create connection to google
        URL url = new URL("https://www.google.com/recaptcha/api/siteverify");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //request header
        conn.setRequestMethod("POST");
        //params string
        String parameters = "secret=" + reCaptchaKeys.getSecretKey() + "&response=" + recaptchaResponse;
        //send post request
        conn.setDoOutput(true);
        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        outStream.writeBytes(parameters);
        outStream.flush();
        outStream.close();


        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        System.out.println(response.toString());
        //parse JSON response and return 'success' value

        return false;
    }
}
