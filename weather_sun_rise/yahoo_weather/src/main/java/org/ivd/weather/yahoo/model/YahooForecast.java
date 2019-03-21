package org.ivd.weather.yahoo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Класс для разбора JSON полученного от сервиса Yahoo Weather API
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YahooForecast {
    private String date;
    private String high;
    private String low;
    private String text;

    public YahooForecast(String date, String high, String low, String text) {

        this.date = date;
        this.high = high;
        this.low = low;
        this.text = text;
    }

    public YahooForecast() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "\ndate: " + date +
                "\nhighTemp: " + high +
                "\nlowTemp: " + low +
                "\ndesc: " + text + "\n------------------";
    }
}
