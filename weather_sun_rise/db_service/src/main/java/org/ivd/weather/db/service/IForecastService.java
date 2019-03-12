package org.ivd.weather.db.service;

/**
 * Сервис для сохранения данных в базу
 */
public interface IForecastService {
    /**
     * Метод сохранения данных о погоде
     * @param msg
     * @throws Exception
     */
    void save(String msg) throws Exception;

}
