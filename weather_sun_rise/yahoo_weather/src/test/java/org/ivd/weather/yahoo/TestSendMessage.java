package org.ivd.weather.yahoo;

import org.easymock.TestSubject;
import org.ivd.weather.yahoo.service.YahooRequestService;
import org.junit.Test;

/**
 * Тесты для модуля yahoo_weather
 */
public class TestSendMessage {

    private String cityEmpty = "";
    private String cityNull = null;

    @TestSubject
    YahooRequestService sendMessage = new YahooRequestService();

    /**
     * Проверка запроса данных о погоде при пустом значении
     * названия города
     *
     * @throws Exception
     */

    @Test(expected = Exception.class)
    public void testEmptyCity() throws Exception {
        sendMessage.getResultYahoo(cityEmpty);
    }

    /**
     * Проверка запроса данных о погоде при значении названия
     * города NULL
     *
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testNullCity() throws Exception {
        sendMessage.getResultYahoo(cityNull);
    }

}
