package org.ivd.weather.yahoo.service;

import org.ivd.weather.error.exception.WeatherException;
import org.ivd.weather.yahoo.model.YahooResult;

/**
 * Сервис передачи сообщения в JMS-очередь
 */
public interface JmsSenderForecast {

    /**
     * Метод отправки сообщений
     *
     * @param result YahooResult
     * @throws WeatherException Исключение
     */
    void sendMessage(YahooResult result) throws WeatherException;
}
