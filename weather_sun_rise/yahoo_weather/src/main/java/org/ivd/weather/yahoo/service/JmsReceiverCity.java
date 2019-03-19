package org.ivd.weather.yahoo.service;

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

/**
 * Класс забирающий из JMS очереди название города
 */
@MessageDriven(name = "JmsReceiverCity", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/queue/cityQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class JmsReceiverCity implements MessageListener {

    private final Logger LOG = LoggerFactory.getLogger(JmsReceiverCity.class);

    private YahooSendMessage yahooSend;

    @Inject
    public JmsReceiverCity(YahooSendMessage yahooSend) {
        this.yahooSend = yahooSend;
    }

    /**
     * Обязательный конструктор без параметров
     */
    public JmsReceiverCity() {
    }

    /**
     * Метод разбора JMS сообщения и передача в сервис для отправки в Yahoo API
     *
     * @param message В параметре хранится название города
     */
    @Override
    public void onMessage(Message message) {
        String city;
        try {
            city = ((TextMessage) message).getText();
            yahooSend.createAndSendMessage(city);
            LOG.info("JmsReceiverCity (onMessage) - > {}", city);
        } catch (JMSException | WeatherException ex) {
            throw new RuntimeException("JmsReceiverCity (JMSException | IOException) -> ", ex);
        }
    }
}
