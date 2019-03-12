package org.ivd.weather.user.service;

import org.ivd.weather.user.model.ForecastReq;
import org.ivd.weather.user.view.ForecastView;

import java.util.Date;

public interface IForecastService {
    ForecastView getForecastByCityAndDate(ForecastReq req) throws Exception;

}
