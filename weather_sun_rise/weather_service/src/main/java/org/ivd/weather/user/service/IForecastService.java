package org.ivd.weather.user.service;

import org.ivd.weather.user.model.ForecastReq;
import org.ivd.weather.user.view.ForecastView;

/**
 * Сервис запроса данных
 */
public interface IForecastService {
    /**
     * Метод запроса данных о прогнозе погоды
     *
     * @param req Объект запроса
     * @return ForecastView
     * @throws Exception Исключение
     */
    ForecastView getForecastByCityAndDate(ForecastReq req) throws Exception;

}
