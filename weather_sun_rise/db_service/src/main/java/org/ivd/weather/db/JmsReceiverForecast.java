package org.ivd.weather.db;

import org.ivd.weather.db.service.IForecastService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

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

    private IForecastService service;

    @Inject
    public JmsReceiverForecast(IForecastService service) {
        this.service = service;
    }

    public JmsReceiverForecast() {
    }

    @Override
    public void onMessage(Message message) {
        String data;
        try {
            data = ((TextMessage) message).getText();

            service.save(data);
            LOG.info("message -> {}", data);
        } catch (JMSException ex) {
            throw new RuntimeException("JmsReceiverForecast (JMSException) -> ", ex);
        } catch (Exception ex) {
            throw new RuntimeException("JmsReceiverForecast (Exception) -> ", ex);
        }
    }
}
