package org.ivd.weather.db.dao;

import org.ivd.weather.db.entity.ForecastEntity;
import org.ivd.weather.error.exception.WeatherException;

import java.util.Date;

/**
 * Класс для связи с базой данных и дальнейших операций
 */
public interface ForecastDao {
    /**
     * Метод сохранения прогноза погоды
     *
     * @param entity Сущность Forecast
     */
    void save(ForecastEntity entity);


    /**
     * Метод обновления данных о прогнозе погоды,
     * если они уже существуют
     *
     * @param entity Сущность Forecast
     */
    void update(ForecastEntity entity);

    /**
     * Проверка наличия прогнозы погоды по названию города и
     * дате
     *
     * @param city Название города
     * @param date Дата
     * @return boolean
     */
    boolean isForecastEmpty(String city, Date date);


    /**
     * Получение прогноза погоды для актуализации данных
     *
     * @param city Название города
     * @param date Дата
     * @return ForecastEntity
     */
    ForecastEntity findByCityAndDate(String city, Date date) throws WeatherException;

}

