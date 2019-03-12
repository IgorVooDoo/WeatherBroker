package org.ivd.weather.user.controller;

import org.ivd.weather.user.model.ForecastReq;
import org.ivd.weather.user.service.IForecastService;
import org.ivd.weather.user.view.ForecastView;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Сервис для получения и обработки запросов
 */
@Controller
@SessionAttributes(value = "data")
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class ForecastController {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(ForecastController.class);

    private IForecastService service;

    @Autowired
    public ForecastController(IForecastService service) {
        this.service = service;
    }

    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    public ModelAndView getForecastReq(@ModelAttribute("data") ForecastReq req) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("data", req);
        mv.addObject("date", req.date);
        mv.addObject("city", req.city);
        mv.setViewName("index");
        return mv;
    }

    @ModelAttribute("data")
    public ForecastReq createFilter() {
        return new ForecastReq();
    }

    @RequestMapping(value = "/weather/submit", method = RequestMethod.GET)
    public ModelAndView getForecast(@ModelAttribute("data") ForecastReq req) {
        ModelAndView mv = new ModelAndView();

        ForecastView forecastView;
        try {
            mv.setViewName("forecast");
            forecastView = service.getForecastByCityAndDate(req);
            mv.addObject("forecastView", forecastView);
        } catch (Exception ex) {
            mv.setViewName("error");
        }
        return mv;
    }

    @RequestMapping(value = "ping", method = RequestMethod.GET)
    public String ping() {
        return "index";
    }
}
