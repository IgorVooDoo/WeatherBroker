package org.ivd.weather.user.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ForecastReq {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date date;
    public String city;

    public ForecastReq(Date date, String city) {
        this.date = date;
        this.city = city;
    }

    public ForecastReq() {
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

    @Override
    public String toString() {
        return "ForecastReq{" +
                "date=" + date +
                ", city='" + city + '\'' +
                '}';
    }
}
