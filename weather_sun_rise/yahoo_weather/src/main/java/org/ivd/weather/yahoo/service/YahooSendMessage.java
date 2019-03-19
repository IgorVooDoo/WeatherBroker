package org.ivd.weather.yahoo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ivd.weather.error.exception.WeatherException;
import org.ivd.weather.tools.model.Forecast;
import org.ivd.weather.yahoo.model.YahooForecast;
import org.ivd.weather.yahoo.model.YahooResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.RequestScoped;
import javax.jms.*;
import javax.jms.Queue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;


@RequestScoped
public class YahooSendMessage implements IYahooSendMessage {
    private final Logger LOG = LoggerFactory.getLogger(YahooSendMessage.class);

    private static final String JMS_QUEUE_WEATHER = "java:jboss/queue/weatherQueue";
    private static final String JMS_CONNECTION_FACTORY_JNDI = "java:comp/DefaultJMSConnectionFactory";//"java:jboss/DefaultJMSConnectionFactory";

    @Resource(name = JMS_QUEUE_WEATHER)
    private Queue queue;

    @Resource(name = JMS_CONNECTION_FACTORY_JNDI)
    private ConnectionFactory connection;

    private ObjectMapper objectMapper = new ObjectMapper();

    public YahooSendMessage() {
    }

    public void createAndSendMessage(String city) throws WeatherException {
        try {
            if (!city.isEmpty()) {
                YahooResult result = getResultYahoo(city);
                JMSContext context = connection.createContext();
                JMSProducer producer = context.createProducer().setDeliveryMode(DeliveryMode.PERSISTENT);
                List<Forecast> listForecast = getForecastForSend(result);
                for (Forecast item : listForecast) {
                    String message = objectMapper.writeValueAsString(item);
                    producer.send(queue, message);
                    LOG.info("Send message: {}", message);
                }
            } else {
                throw new WeatherException("Наименование города не может быть NULL");
            }
        } catch (IOException ex) {
            throw new WeatherException("Ошибки в преобразовании форматов");
        }

    }

    private YahooResult getResultYahoo(String city) throws IOException, WeatherException {
        String url = "https://weather-ydn-yql.media.yahoo.com/forecastrss?location=" + city + "&format=json&u=c";
        String authorizationLine = getAuthorizationString(city);
        LOG.info("authorizationLine - > {}", authorizationLine);
        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", authorizationLine);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return objectMapper.readValue(response.toString(), YahooResult.class);
    }

    private String getAuthorizationString(String city) throws IOException, WeatherException {
        final String consumerKey = "dj0yJmk9S3JuM2xQWTV5TWNlJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PWM4";
        final String consumerSecret = "72cc6743746771389325530d0cb5cacd495f32ad";
        final String url = "https://weather-ydn-yql.media.yahoo.com/forecastrss";

        long timestamp = new Date().getTime() / 1000;
        byte[] nonce = new byte[32];
        Random rand = new Random();
        rand.nextBytes(nonce);
        String oauthNonce = new String(nonce).replaceAll("\\W", "");

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

        String signatureString = "GET&" +
                URLEncoder.encode(url, "UTF-8") + "&" +
                URLEncoder.encode(parametersList.toString(), "UTF-8");

        String signature;
        try {
            SecretKeySpec signingKey = new SecretKeySpec((consumerSecret + "&").getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHMAC = mac.doFinal(signatureString.getBytes());
            Base64.Encoder encoder = Base64.getEncoder();
            signature = encoder.encodeToString(rawHMAC);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new WeatherException("YahooSendMessage (getAuthorizationString()) ->", e);
        }

        return "OAuth " +
                "oauth_consumer_key=\"" + consumerKey + "\"," +
                "oauth_nonce=\"" + oauthNonce + "\"," +
                "oauth_timestamp=\"" + timestamp + "\"," +
                "oauth_signature_method=\"HMAC-SHA1\"," +
                "oauth_signature=\"" + signature + "\"," +
                "oauth_version=\"1.0\"";
    }

    private List<Forecast> getForecastForSend(YahooResult result) {
        List<Forecast> listForecast = new ArrayList<>();
        YahooForecast[] arrYahooForecast = result.getForecasts();

        for (YahooForecast item : arrYahooForecast) {
            Forecast send = new Forecast();
            send.setCity(result.getLocation().getCity());
            send.setDate(item.getDate());
            send.setHigh(item.getHigh());
            send.setLow(item.getLow());
            send.setText(item.getText());
            listForecast.add(send);
        }
        return listForecast;
    }
}
