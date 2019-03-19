package org.ivd.weather.error.exception;

/**
 * Исключение, генерируется в результате работы приложения
 */
public class WeatherException extends Exception {
    public WeatherException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public WeatherException(String msg) {
        super(msg);
    }
}
