package org.ivd.weather.db.dao;

import org.ivd.weather.db.entity.ForecastEntity;

import java.util.Date;

/**
 * Класс для связи с базой данных и дальнейших операций
 */
public interface IForecastDao {
    /**
     * Метод сохранения прогноза погоды
     * @param entity
     */
    void save(ForecastEntity entity);

    /**
     * Метод обновления данных о прогнозе погоды,
     * если они уже существуют
     * @param entity
     */
    void update(ForecastEntity entity);

    /**
     * Проверка наличия прогнозы погоды по названию города и
     * дате
     * @param city
     * @param date
     * @return
     */
    boolean isForecast(String city, Date date);

    /**
     * Получение прогноза погоды для актуализации данных
     * @param city
     * @param date
     * @return
     */
    ForecastEntity getByCityAndDate(String city, Date date);

}
