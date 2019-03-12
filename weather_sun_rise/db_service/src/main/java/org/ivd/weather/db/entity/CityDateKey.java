package org.ivd.weather.db.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CityDateKey implements Serializable {
    @Column(name = "city")
    private String city;
    @Column(name = "dateforecast")
    private String date;

    public CityDateKey(String city, String date) {
        this.city = city;
        this.date = date;
    }

    public CityDateKey() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
