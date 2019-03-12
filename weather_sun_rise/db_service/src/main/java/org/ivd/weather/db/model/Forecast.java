package org.ivd.weather.db.model;

/**
 * Модель данных из JMS сообщении
 */
public class Forecast {
    private String city;
    private String high;
    private String low;
    private String text;
    private String date;

    public Forecast(String city, String high, String low, String text, String date) {
        this.city = city;
        this.high = high;
        this.low = low;
        this.text = text;
        this.date = date;
    }

    public Forecast() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
