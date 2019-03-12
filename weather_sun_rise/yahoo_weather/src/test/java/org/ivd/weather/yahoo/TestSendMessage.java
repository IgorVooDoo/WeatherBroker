package org.ivd.weather.yahoo;

import org.easymock.TestSubject;
import org.junit.Test;

/**
 * Тесты для модуля yahoo_weather
 */
public class TestSendMessage {

    private String cityEmpty = "";
    private String cityNull = null;

    @TestSubject
    YahooSendMessage sendMessage = new YahooSendMessage();

    /**
     * Проверка запроса данных о погоде при пустом значении
     * названия города
     *
     * @throws Exception
     */

    @Test(expected = Exception.class)
    public void testEmptyCity() throws Exception {
        sendMessage.getData(cityEmpty);
    }

    /**
     * Проверка запроса данных о погоде при значении названия
     * города NULL
     *
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testNullCity() throws Exception {
        sendMessage.getData(cityNull);
    }

}
