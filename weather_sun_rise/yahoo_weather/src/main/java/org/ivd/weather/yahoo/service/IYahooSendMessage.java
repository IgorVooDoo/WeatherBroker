package org.ivd.weather.yahoo.service;

import org.ivd.weather.error.exception.WeatherException;
import org.ivd.weather.yahoo.model.YahooResult;

import java.io.IOException;

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
    YahooResult getResultYahoo(String city) throws IOException, WeatherException;
}
