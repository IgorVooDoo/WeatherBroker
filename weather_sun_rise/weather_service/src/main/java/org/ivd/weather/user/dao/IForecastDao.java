package org.ivd.weather.user.dao;



import org.ivd.weather.user.entity.ForecastEntity;

import java.util.Date;


public interface IForecastDao {
    ForecastEntity findByCityDate(Date date, String city);
}
