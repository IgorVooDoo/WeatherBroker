package org.ivd.weather.yahoo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * Класс для разбора JSON полученного от сервиса Yahoo Weather API
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YahooResult {

    private YahooForecast[] forecasts;
    private YahooLocation location;

    public YahooResult(YahooForecast[] forecasts, YahooLocation location) {
        this.forecasts = forecasts;
        this.location = location;
    }

    public YahooResult() {
    }

    public YahooForecast[] getForecasts() {
        return forecasts;
    }

    public void setForecasts(YahooForecast[] forecasts) {
        this.forecasts = forecasts;
    }

    public YahooLocation getLocation() {
        return location;
    }

    public void setLocation(YahooLocation location) {
        this.location = location;
    }

}
