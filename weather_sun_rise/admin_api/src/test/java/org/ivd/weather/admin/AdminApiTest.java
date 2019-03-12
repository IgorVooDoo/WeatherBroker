package org.ivd.weather.admin;

import org.easymock.TestSubject;
import org.junit.Test;

/**
 * Тесты для модуля admin_api.
 */
public class AdminApiTest {
    private String cityEmpty = "";
    private String cityNull = null;

    @TestSubject
    JmsSenderCity senderCity = new JmsSenderCity();

    /**
     * Тест на отправку пустого значения города
     *
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testEmptyCity() {
        senderCity.sendMessage(cityEmpty);
    }

    /**
     * Тест на отправку города со значением NULL
     *
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testNullCity() {
        senderCity.sendMessage(cityNull);
    }
}
