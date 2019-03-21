package org.ivd.weather.db;

import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.ivd.weather.db.dao.ForecastDaoImpl;
import org.ivd.weather.db.service.ForecastServiceImpl;
import org.junit.Rule;
import org.junit.Test;

import static org.easymock.EasyMock.mock;

/**
 * Тесты для модуля db_service
 */
public class ForecastServiceImplTest {

    private String msgEmpty = "";
    private String msgNull = null;

    public ForecastServiceImplTest() {
    }

    @Rule
    public EasyMockRule em = new EasyMockRule(this);

    @Mock(MockType.NICE)
    private ForecastDaoImpl dao = mock(ForecastDaoImpl.class);

    @TestSubject
    private ForecastServiceImpl forecastServiceImpl = new ForecastServiceImpl();

    /**
     * Проверка на попытку записать в базу значение NULL
     *
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void saveNullTest() throws Exception {
        forecastServiceImpl.save(msgNull);
    }

    /**
     * Проверка на запись пустого значения
     *
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void saveEmptyTest() throws Exception {
        forecastServiceImpl.save(msgEmpty);
    }

}
