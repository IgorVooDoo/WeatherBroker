package org.ivd.weather.admin;

import org.ivd.weather.error.exception.WeatherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

/**
 * Класс для отправки названия города в JMS очередь
 */
@RequestScoped
public class JmsSenderCity {
    private final Logger LOG = LoggerFactory.getLogger(JmsSenderCity.class);

    private static final String JMS_QUEUE_CITY = "java:jboss/queue/cityQueue";
    private static final String JMS_CONNECTION_FACTORY_JNDI = "java:comp/DefaultJMSConnectionFactory";//"java:jboss/DefaultJMSConnectionFactory";

    @Resource(name = JMS_QUEUE_CITY)
    private Queue queue;

    @Resource(name = JMS_CONNECTION_FACTORY_JNDI)
    private ConnectionFactory connection;

    JmsSenderCity() {
    }

    /**
     * Создание и отправка сообщения с названием города
     *
     * @param msg название города
     */
    void sendMessage(String msg) throws WeatherException {
        if (msg.isEmpty()) {
            throw new WeatherException("Название города отсутствует");
        }
        try {
            JMSContext context = connection.createContext();
            JMSProducer producer = context.createProducer();
            producer.send(queue, msg);
            LOG.info("JmsSenderCity (sendMessage)-> {}", msg);
        } catch (Exception ex) {
            throw new RuntimeException("sendMessage error -> ", ex);
        }

    }
}
