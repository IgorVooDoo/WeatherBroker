package org.ivd.weather.admin;

import org.easymock.TestSubject;
import org.junit.Test;

public class AdminApiTest {
    private String city = "Saratov";
    private String cityEmpty = "";
    private String cityNull = null;

    @TestSubject
    JmsSenderCity senderCity = new JmsSenderCity();

    @Test
    public void testEmptyCity(){
        senderCity.sendMessage(cityEmpty);
    }

    @Test
    public void testNullCity(){
        senderCity.sendMessage(cityNull);
    }



}
