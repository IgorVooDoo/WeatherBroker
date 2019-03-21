package org.ivd.weather.db.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.ivd.weather.db.dao.ForecastDao;
import org.ivd.weather.db.entity.ForecastEntity;
import org.ivd.weather.error.exception.WeatherException;
import org.ivd.weather.tools.model.Forecast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;

/**
 * {@inheritDoc}
 */
@RequestScoped
public class ForecastServiceImpl implements ForecastService {
    private final Logger LOG = LoggerFactory.getLogger(ForecastServiceImpl.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    private ForecastDao dao;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void save(String msg) throws WeatherException, IOException {
        if (StringUtils.isEmpty(msg)) {
            throw new WeatherException("Пустое сообщение");
        }
        Forecast forecast = objectMapper.readValue(msg, Forecast.class);
        Date date = getDateFromString(forecast.getDate());
        String city = forecast.getCity();

        ForecastEntity entity = getForecastAsEntity(forecast);
        if (dao.isForecastEmpty(city, date)) {
            dao.save(entity);
            LOG.info("Сохранили entity: {}", entity);
        } else {
            ForecastEntity entityForUpdate = dao.findByCityAndDate(city, date);
            entityForUpdate.setHighTemp(entity.getHighTemp());
            entityForUpdate.setLowTemp(entity.getLowTemp());
            entityForUpdate.setDescription(entity.getDescription());
            dao.update(entityForUpdate);
            LOG.info("Обновили entity: {}", entity);
        }
    }

    private ForecastEntity getForecastAsEntity(Forecast forecast) {
        ForecastEntity entity = new ForecastEntity();
        Date date = getDateFromString(forecast.getDate());
        entity.setDateForecast(date);
        entity.setCity(forecast.getCity());
        entity.setHighTemp(forecast.getHigh());
        entity.setLowTemp(forecast.getLow());
        entity.setDescription(forecast.getText());
        return entity;
    }

    private Date getDateFromString(String date) {
        return new Date(Long.parseLong(date) * 1000L);
    }
}
