package org.ivd.weather.yahoo;

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

    @Inject
    public JmsReceiverCity(YahooSendMessage yahooSend) {
        this.yahooSend = yahooSend;
    }

    public JmsReceiverCity() {
    }

    /**
     * Метод разбора JMS сообщения и передача в сервис для отправки в Yahoo API
     * @param message
     */

    @Override
    public void onMessage(Message message) {
        String city = "";
        try {
            city = ((TextMessage) message).getText();
            yahooSend.getData(city);
            LOG.info("JmsReceiverCity (onMessage) - > {}", city);
        } catch (JMSException | IOException ex) {
            throw new RuntimeException("JmsReceiverCity (JMSException | IOException) -> ",ex);
        } catch (Exception ex) {
            throw new RuntimeException("JmsReceiverCity (Exception) -> ",ex);
        }
    }
}
