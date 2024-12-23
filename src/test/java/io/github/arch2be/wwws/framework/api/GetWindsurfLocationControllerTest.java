package io.github.arch2be.wwws.framework.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
class GetWindsurfLocationControllerTest {

    @Autowired
    private GetWindsurfLocationController getWindsurfLocationController;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private String dateWithCorrectFormat;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(getWindsurfLocationController).build();

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateWithCorrectFormat = currentDate.format(formatter);
    }

    @Test
    void getTheBestWindsurfLocationConditionForDate_whenPassWrongDate_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/windsurf-location").param("date", "01-01-1999"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void getTheBestWindsurfLocationConditionForDate_whenWeatherForecastReturnData_shouldReturnCorrectResponse() throws Exception {
        // Given:
        WindsurfWeatherResponse expectedResponse = new WindsurfWeatherResponse("Jastarnia", 10.0, 15.0);

        // When:
        stubFor(WireMock.get(urlEqualTo(format("/forecast/daily?city=%s&key=%s", "Jastarnia", "mockedapikey")))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(format("""
                                {"data": [{"datetime": "%s","temp": 10,"wind_spd": 15}]}
                                """, dateWithCorrectFormat))
                ));

        // Then:
        MvcResult mvcResult = mockMvc.perform(get("/windsurf-location").param("date", dateWithCorrectFormat))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        WindsurfWeatherResponse actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), WindsurfWeatherResponse.class);

        assertEquals(expectedResponse, actualResponse);
    }
}