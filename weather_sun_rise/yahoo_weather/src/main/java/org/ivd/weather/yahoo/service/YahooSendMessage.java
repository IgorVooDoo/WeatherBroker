package org.ivd.weather.yahoo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ivd.weather.error.exception.WeatherException;
import org.ivd.weather.tools.service.CheckService;
import org.ivd.weather.yahoo.model.YahooResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.RequestScoped;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * {@inheritDoc}
 */
@RequestScoped
public class YahooSendMessage implements IYahooSendMessage {
    private final Logger LOG = LoggerFactory.getLogger(YahooSendMessage.class);

    private final static String consumerKey = "dj0yJmk9S3JuM2xQWTV5TWNlJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PWM4";
    private final static String consumerSecret = "72cc6743746771389325530d0cb5cacd495f32ad";
    private final static String url = "https://weather-ydn-yql.media.yahoo.com/forecastrss";
    private ObjectMapper objectMapper = new ObjectMapper();
    private CheckService check = new CheckService();

    /**
     * {@inheritDoc}
     */
    public YahooResult getResultYahoo(String city) throws IOException, WeatherException {
        if (check.isNullOrEmpty(city)) {
            throw new WeatherException("Наименование города не может быть NULL");
        }
        String url = "https://weather-ydn-yql.media.yahoo.com/forecastrss?location=" + city + "&format=json&u=c";
        String authorizationLine = getAuthorizationString(city);
        LOG.info("authorizationLine - > {}", authorizationLine);
        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", authorizationLine);
        String response = getResponseString(con);
        con.disconnect();
        return objectMapper.readValue(response, YahooResult.class);
    }

    private String getResponseString(HttpURLConnection con) throws WeatherException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException ex) {
            throw new WeatherException("Ошибка преобразования ответа", ex);
        }
        return response.toString();
    }

    private String getAuthorizationString(String city) throws IOException, WeatherException {

        long timestamp = new Date().getTime() / 1000;

        String oauthNonce = getOAuthNonce();

        String signatureString = getSignatureString(oauthNonce, timestamp, city);

        String signature = getSignature(signatureString);

        return "OAuth " +
                "oauth_consumer_key=\"" + consumerKey + "\"," +
                "oauth_nonce=\"" + oauthNonce + "\"," +
                "oauth_timestamp=\"" + timestamp + "\"," +
                "oauth_signature_method=\"HMAC-SHA1\"," +
                "oauth_signature=\"" + signature + "\"," +
                "oauth_version=\"1.0\"";
    }

    private String getSignatureString(String oauthNonce, long timestamp, String city) throws IOException {
        List<String> parameters = new ArrayList<>();
        parameters.add("oauth_consumer_key=" + consumerKey);
        parameters.add("oauth_nonce=" + oauthNonce);
        parameters.add("oauth_signature_method=HMAC-SHA1");
        parameters.add("oauth_timestamp=" + timestamp);
        parameters.add("oauth_version=1.0");
        parameters.add("location=" + URLEncoder.encode(city, "UTF-8"));
        parameters.add("format=json");
        parameters.add("u=c");

        Collections.sort(parameters);

        StringBuilder parametersList = new StringBuilder();

        for (int i = 0; i < parameters.size(); i++) {
            parametersList.append((i > 0) ? "&" : "").append(parameters.get(i));
        }

        return "GET&" +
                URLEncoder.encode(url, "UTF-8") + "&" +
                URLEncoder.encode(parametersList.toString(), "UTF-8");
    }

    private String getSignature(String signatureString) throws WeatherException {
        try {
            SecretKeySpec signingKey = new SecretKeySpec((consumerSecret + "&").getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHMAC = mac.doFinal(signatureString.getBytes());
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(rawHMAC);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new WeatherException("YahooSendMessage (getAuthorizationString()) ->", e);
        }
    }

    private String getOAuthNonce() {
        byte[] nonce = new byte[32];
        Random rand = new Random();
        rand.nextBytes(nonce);
        return new String(nonce).replaceAll("\\W", "");
    }


}
