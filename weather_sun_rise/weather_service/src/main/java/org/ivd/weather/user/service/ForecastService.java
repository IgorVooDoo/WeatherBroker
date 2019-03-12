package org.ivd.weather.user.service;

import org.ivd.weather.user.dao.ForecastDao;
import org.ivd.weather.user.entity.ForecastEntity;
import org.ivd.weather.user.model.ForecastReq;
import org.ivd.weather.user.view.ForecastView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ForecastService implements IForecastService {
    private final Logger log = LoggerFactory.getLogger(ForecastService.class);
    private ForecastDao dao;

    @Autowired
    public ForecastService(ForecastDao dao) {
        this.dao = dao;
    }

    @Transactional
    public ForecastView getForecastByCityAndDate(ForecastReq req) throws Exception {
        if (req == null) {
            throw new Exception("Empty request");
        }
        ForecastEntity entity = dao.findByCityDate(req.getDate(), req.getCity());
        return generateViewFromEntity(entity);
    }

    private ForecastView generateViewFromEntity(ForecastEntity entity) {
        ForecastView view = new ForecastView();
        view.setCity(entity.getCity());
        view.setDate(entity.getDateForecast());
        view.setHighTemp(entity.getHighTemp());
        view.setLowTemp(entity.getLowTemp());
        view.setDescription(entity.getDescription());
        return view;

    }
}
