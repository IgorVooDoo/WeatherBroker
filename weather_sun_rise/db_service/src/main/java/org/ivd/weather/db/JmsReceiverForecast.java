package org.ivd.weather.db;

import org.ivd.weather.db.service.ForecastService;
import org.ivd.weather.error.exception.WeatherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * Класс для получения JMS сообщений из очереди прогноза погоды,
 * разбора и записи данных в базу
 */
@MessageDriven(name = "JmsReceiverForecast", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/queue/weatherQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class JmsReceiverForecast implements MessageListener {
    private final Logger LOG = LoggerFactory.getLogger(JmsReceiverForecast.class);

    @Inject
    private ForecastService service;

    @Override
    public void onMessage(Message message) {
        String data;
        try {
            data = ((TextMessage) message).getText();
            service.save(data);
            LOG.info("message -> {}", data);
        } catch (JMSException | IOException| WeatherException ex) {
            throw new RuntimeException("JmsReceiverForecast (JMSException) -> ", ex);
        }
    }
}
