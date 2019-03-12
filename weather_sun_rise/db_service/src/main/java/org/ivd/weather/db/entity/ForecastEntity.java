package org.ivd.weather.db.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Сущность для связи с таблицей прогноза погоды
 */
@Entity
@Table(name = "forecast")
public class ForecastEntity {
    @Id @GeneratedValue(generator="hibseq")
    @GenericGenerator(name="hibseq", strategy = "seqhilo")
    @Column(name = "id")
    private Long id;
    @Version
    private Integer version;
    @NotNull
    @Column(name = "dateforecast")
    private Date dateForecast;
    @NotNull
    @Column(name = "city")
    private String city;
    @NotNull
    @Column(name = "hightemp")
    private String highTemp;
    @NotNull
    @Column(name = "lowtemp")
    private String lowTemp;
    @NotNull
    @Column(name = "description")
    private String description;

    public ForecastEntity(Date dateForecast, String city, String highTemp, String lowTemp, String description) {
        this.dateForecast = dateForecast;
        this.city = city;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.description = description;
    }

    public ForecastEntity() {
    }

    public Long getId() {
        return id;
    }

    public Date getDateForecast() {
        return dateForecast;
    }

    public void setDateForecast(Date date) {
        this.dateForecast = date;
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

    public String toString() {
        return "\nid: " + id +
                "\ncity: " + city +
                "\ndate: " + dateForecast +
                "\nhighTemp: " + highTemp +
                "\nlowTemp: " + lowTemp +
                "\ndesc: " + description + "\n------------------";
    }
}
