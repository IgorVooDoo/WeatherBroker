package org.ivd.weather.yahoo.service;
/**
 * Класс получения данных о погоде от сервиса Yahoo,
 * отправка данный в JMS очередь
 */
public interface IYahooSendMessage {
    /**
     * Метод для получения данных от сервиса Yahoo Weather API,
     * и отправка сообщения в JMS очередь
     *
     * @param city Название города
     * @throws Exception Исключение
     */
    void createAndSendMessage(String city) throws Exception;
}
