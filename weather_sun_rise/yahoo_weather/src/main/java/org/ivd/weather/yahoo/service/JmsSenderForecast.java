package org.ivd.weather.yahoo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ivd.weather.error.exception.WeatherException;
import org.ivd.weather.tools.model.Forecast;
import org.ivd.weather.yahoo.model.YahooForecast;
import org.ivd.weather.yahoo.model.YahooResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис передачи сообщения в JMS-очередь
 */
@RequestScoped
public class JmsSenderForecast {
    private final Logger LOG = LoggerFactory.getLogger(JmsSenderForecast.class);
    private static final String JMS_QUEUE_WEATHER = "java:jboss/queue/weatherQueue";
    private static final String JMS_CONNECTION_FACTORY_JNDI = "java:comp/DefaultJMSConnectionFactory";

    @Resource(name = JMS_QUEUE_WEATHER)
    private Queue queue;

    @Inject
    @JMSConnectionFactory(JMS_CONNECTION_FACTORY_JNDI)
    private JMSContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Метод отправки сообщений
     *
     * @param result YahooResult
     * @throws WeatherException Исключение
     */
    public void sendMessage(YahooResult result) throws WeatherException {
        if (result != null) {
            try {
                JMSProducer producer = context.createProducer().setDeliveryMode(DeliveryMode.PERSISTENT);
                List<Forecast> listForecast = getForecastForSend(result);
                for (Forecast item : listForecast) {
                    String message = objectMapper.writeValueAsString(item);
                    producer.send(queue, message);
                    LOG.info("Send message: {}", message);
                }
            } catch (IOException ex) {
                throw new WeatherException("Ошибка при преобразовании в JSON");
            }
        } else {
            throw new WeatherException("Получен пустой объект ");
        }

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
