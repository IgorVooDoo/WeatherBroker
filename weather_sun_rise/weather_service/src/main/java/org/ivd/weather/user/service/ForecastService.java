package org.ivd.weather.user.service;

import org.ivd.weather.error.exception.WeatherException;
import org.ivd.weather.user.model.ForecastReq;
import org.ivd.weather.user.view.ForecastView;

/**
 * Сервис запроса данных
 */
public interface ForecastService {
    /**
     * Метод запроса данных о прогнозе погоды
     *
     * @param req Объект запроса
     * @return ForecastView
     * @throws WeatherException Исключение
     */
    ForecastView getForecastByCityAndDate(ForecastReq req) throws WeatherException;

}
