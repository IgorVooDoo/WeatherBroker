package org.ivd.weather.user;


import org.easymock.EasyMockRule;
import org.ivd.weather.user.config.ServiceConfig;
import org.ivd.weather.user.controller.ForecastController;
import org.ivd.weather.user.service.ForecastService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.easymock.EasyMock.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class})
@WebAppConfiguration
public class ForecastControllerTest {
    private final Logger log = LoggerFactory.getLogger(ForecastControllerTest.class);

    @Rule
    public EasyMockRule mockRule = new EasyMockRule(this);

    private MockMvc mockMvc;

    private ForecastService mockForecastService = mock(ForecastService.class);

    @Before
    public void ForecastControllerTest(){
        mockMvc = MockMvcBuilders.standaloneSetup(new ForecastController(mockForecastService)).build();
    }

    @Test
    public void testGetWeatherCityDate() throws Exception {
        mockMvc.perform(get("/api/weather/submit")
                .param("date", "2019-03-15")
                .param("city", "Saratov"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
