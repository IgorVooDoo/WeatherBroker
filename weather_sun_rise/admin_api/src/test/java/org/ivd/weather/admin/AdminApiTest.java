package org.ivd.weather.admin;

import org.easymock.TestSubject;
import org.ivd.weather.admin.service.JmsSenderCityImpl;
import org.ivd.weather.error.exception.WeatherException;
import org.junit.Test;

/**
 * Тесты для модуля admin_api.
 */
public class AdminApiTest {
    private String cityEmpty = "";
    private String cityNull = null;

    @TestSubject
    JmsSenderCityImpl senderCity = new JmsSenderCityImpl();

    /**
     * Тест на отправку пустого значения города
     *
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testEmptyCity() throws WeatherException {
        senderCity.sendMessage(cityEmpty);
    }

    /**
     * Тест на отправку города со значением NULL
     *
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testNullCity() throws WeatherException {
        senderCity.sendMessage(cityNull);
    }
}
