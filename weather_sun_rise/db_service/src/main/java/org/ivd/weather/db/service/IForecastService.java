package org.ivd.weather.db.service;

import org.ivd.weather.error.exception.WeatherException;

import java.io.IOException;

/**
 * Сервис для сохранения данных в базу
 */
public interface IForecastService {

    /**
     * Метод сохранения данных о погоде
     *
     * @param msg JSON прогноза погоды
     * @throws WeatherException Обрабатываемое исключение
     * @throws IOException      Обрабатываемое исключение
     */
    void save(String msg) throws WeatherException, IOException;

}
