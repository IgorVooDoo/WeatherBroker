package org.ivd.weather.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.jms.*;
import javax.enterprise.context.RequestScoped;

/**
 * Класс для отправки названия города в JMS очередь
 */
@RequestScoped
public class JmsSenderCity {
    private final Logger log = LoggerFactory.getLogger(JmsSenderCity.class);

    private static final String JMS_QUEUE_CITY = "java:jboss/queue/cityQueue";
    private static final String JMS_CONNECTION_FACTORY_JNDI = "java:comp/DefaultJMSConnectionFactory";//"java:jboss/DefaultJMSConnectionFactory";

    @Resource(name = JMS_QUEUE_CITY)
    private Queue queue;

    @Resource(name = JMS_CONNECTION_FACTORY_JNDI)
    private ConnectionFactory connection;

    public JmsSenderCity(){}

    /**
     * Создание и отправка сообщения с названием города
     * @param msg
     */
    public void sendMessage(String msg){
        try {
            JMSContext context = connection.createContext();
            JMSProducer producer = context.createProducer();
            producer.send(queue, msg);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        log.info("JmsSenderCity (sendMessage)-> {}",msg);
    }
}
