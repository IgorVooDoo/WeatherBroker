package org.ivd.weather.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет для получения названия города из запроса
 */
@WebServlet(urlPatterns = "/read")
public class RequestCity extends HttpServlet {
    private final Logger LOG = LoggerFactory.getLogger(RequestCity.class);

    @Inject
    private JmsSenderCity senderCity;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("index.jsp")
                .forward(req, resp);
        String city = req.getParameter("city");
        if (!city.isEmpty()) {
            senderCity.sendMessage(city);
            LOG.info("RequestCity (doGet) - > {}", city);
        }
    }
}
