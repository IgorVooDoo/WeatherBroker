package org.ivd.weather.db.dao;

import org.ivd.weather.db.entity.ForecastEntity;

import java.util.Date;

/**
 * TODO - описание класса
 */
public interface IForecastDao {
    void save(ForecastEntity entity);
    void update(ForecastEntity entity);
    boolean isForecast(String city, Date date);
    void find();
    ForecastEntity getByCityAndDate(String city, Date date);

}
