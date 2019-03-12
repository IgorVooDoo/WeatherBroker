package org.ivd.weather.db;

import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.ivd.weather.db.dao.ForecastDao;
import org.ivd.weather.db.model.Forecast;
import org.ivd.weather.db.service.ForecastService;
import org.junit.Rule;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.easymock.EasyMock.*;


public class ForecastServiceTest {

    private String msgEmpty = "";
    private String msgNull = null;

    public ForecastServiceTest(){
    }

    @Rule
    public EasyMockRule em = new EasyMockRule(this);

    @Mock(MockType.NICE)
    private ForecastDao dao = mock(ForecastDao.class);

    @TestSubject
    private ForecastService forecastService = new ForecastService(dao);

    @Test(expected = Exception.class)
    public void saveNullTest() throws Exception {
        forecastService.save(msgNull);
    }

    @Test(expected = Exception.class)
    public void saveEmptyTest() throws Exception {
        forecastService.save(msgEmpty);
    }

}
