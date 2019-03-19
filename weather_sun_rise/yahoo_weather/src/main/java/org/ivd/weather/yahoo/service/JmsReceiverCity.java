package org.ivd.weather.yahoo.service;

import org.ivd.weather.error.exception.WeatherException;
import org.ivd.weather.yahoo.model.YahooResult;
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
    private JmsSenderForecast senderForecast;

    @Inject
    public JmsReceiverCity(YahooSendMessage yahooSend, JmsSenderForecast senderForecast) {
        this.yahooSend = yahooSend;
        this.senderForecast = senderForecast;
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
            YahooResult result = yahooSend.getResultYahoo(city);
            senderForecast.sendMessage(result);
            LOG.info("JmsReceiverCity (onMessage) - > {}", city);
        } catch (JMSException | IOException | WeatherException ex) {
            throw new RuntimeException("JmsReceiverCity (JMSException | IOException) -> ", ex);
        }
    }
}
