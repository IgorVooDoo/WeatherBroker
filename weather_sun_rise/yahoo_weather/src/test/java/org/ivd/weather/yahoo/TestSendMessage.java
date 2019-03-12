package org.ivd.weather.yahoo;

import org.easymock.TestSubject;
import org.junit.Test;

public class TestSendMessage {

    private String cityEmpty = "";
    private String cityNull = null;

    @TestSubject
    YahooSendMessage sendMessage = new YahooSendMessage();


    @Test(expected = Exception.class)
    public void testEmptyCity()throws Exception{
        sendMessage.getData(cityEmpty);
    }

    @Test(expected = Exception.class)
    public void testNullCity()throws Exception{
        sendMessage.getData(cityNull);
    }

}
