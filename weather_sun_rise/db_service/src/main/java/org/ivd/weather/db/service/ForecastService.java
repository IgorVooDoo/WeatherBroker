package org.ivd.weather.db.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ivd.weather.db.dao.IForecastDao;
import org.ivd.weather.db.entity.ForecastEntity;
import org.ivd.weather.tools.model.Forecast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;

@RequestScoped
public class ForecastService implements IForecastService {
    private final Logger LOG = LoggerFactory.getLogger(ForecastService.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    private IForecastDao dao;

    @Inject
    public ForecastService(IForecastDao dao) {
        this.dao = dao;
    }

    public ForecastService(){}

    @Override
    @Transactional
    public void save(String msg) throws Exception {
        if (msg.isEmpty()) {
            throw new Exception("Пустое сообщение");
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
