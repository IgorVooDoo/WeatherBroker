package org.ivd.weather.admin.service;

import org.ivd.weather.error.exception.WeatherException;

/**
 * Класс для отправки названия города в JMS очередь
 */
public interface JmsSenderCity {
    /**
     * Создание и отправка сообщения с названием города
     *
     * @param msg название города
     */
    void sendMessage(String msg) throws WeatherException;
}
