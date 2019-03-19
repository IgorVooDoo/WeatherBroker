package org.ivd.weather.user.controller;

import org.ivd.weather.error.exception.WeatherException;
import org.ivd.weather.user.model.ForecastReq;
import org.ivd.weather.user.service.IForecastService;
import org.ivd.weather.user.view.ErrorView;
import org.ivd.weather.user.view.ForecastView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Сервис для получения и обработки запросов
 */
@Controller
@SessionAttributes(value = "data")
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class ForecastController {
    private final Logger LOG = LoggerFactory.getLogger(ForecastController.class);
    private final IForecastService service;

    @Autowired
    public ForecastController(IForecastService service) {
        this.service = service;
    }

    /**
     * Запрос предоставляющий начальный данные для заполнения
     *
     * @param req Объект запроса
     * @return ModelAndView
     */
    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    public ModelAndView getForecastReq(@ModelAttribute("data") ForecastReq req) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("data", req);
        mv.addObject("date", req.getDate());
        mv.addObject("city", req.getCity());
        mv.setViewName("index");
        return mv;
    }

    @ModelAttribute("data")
    protected ForecastReq createFilter() {
        return new ForecastReq();
    }

    /**
     * В данном запросе принимаем название города и дату
     * возвращаем объект погоды
     *
     * @param req Объект запроса
     * @return ModelAndView
     */
    @RequestMapping(value = "/weather/submit", method = RequestMethod.GET)
    public ModelAndView getForecast(@ModelAttribute("data") ForecastReq req) {
        ModelAndView mv = new ModelAndView();
        LOG.info("В методе GET Submit");
        ForecastView forecastView;
        try {
            mv.setViewName("forecast");
            forecastView = service.getForecastByCityAndDate(req);
            mv.addObject("forecastView", forecastView);
        } catch (WeatherException ex) {
            mv.setViewName("error");
            mv.addObject("errorView",new ErrorView(ex.getMessage()));
        }
        return mv;
    }
}
