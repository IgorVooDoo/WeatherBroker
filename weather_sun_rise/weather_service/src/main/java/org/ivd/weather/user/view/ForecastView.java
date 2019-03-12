package org.ivd.weather.user.view;

import java.util.Date;

/**
 * Представление объекта прогноза погоды
 */
public class ForecastView {
    private Date date;
    private String city;
    private String highTemp;
    private String lowTemp;
    private String description;

    public ForecastView(Date date, String city, String highTemp, String lowTemp, String description) {
        this.date = date;
        this.city = city;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.description = description;
    }

    public ForecastView() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ForecastView{" +
                "date=" + date +
                ", city='" + city + '\'' +
                ", highTemp='" + highTemp + '\'' +
                ", lowTemp='" + lowTemp + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
