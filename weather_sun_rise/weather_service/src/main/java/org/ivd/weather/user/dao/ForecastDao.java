package org.ivd.weather.user.dao;


import org.ivd.weather.error.exception.WeatherException;
import org.ivd.weather.user.entity.ForecastEntity;

import java.util.Date;

/**
 * Класс для связи с базой данных
 */
public interface ForecastDao {
    /**
     * Метод для получения данных о прогнозе погоды
     *
     * @param date Дата
     * @param city Город
     * @return ForecastEntity
     */
    ForecastEntity findByCityDate(Date date, String city) throws WeatherException;
}
