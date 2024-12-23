package io.github.arch2be.wwws.framework.feign;

import io.github.arch2be.wwws.domain.WindsurfWeather;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherForecastImplTest {

    @Mock
    private FeignClientConfiguration feignClientConfiguration;

    @Mock
    private WeatherbitFeignClient weatherbitFeignClient;

    @InjectMocks
    private WeatherForecastImpl sut;


    @Test
    void getForecastForLocationAndDate_whenValidDataReturn_shouldReturnCorrectWindsurfWeather() {
        // Given:
        final WeatherForecastResponse.Forecast forecast = new WeatherForecastResponse.Forecast();
        forecast.setDatetime(LocalDate.now());
        forecast.setTemp(10.0);
        forecast.setWind_spd(10.0);

        final WeatherForecastResponse weatherForecastResponse = new WeatherForecastResponse();
        weatherForecastResponse.setData(Collections.singletonList(forecast));

        final WindsurfWeather expectedWindsurfWeather = new WindsurfWeather("city1", 10.0, 10.0);

        // When:
        when(feignClientConfiguration.getApikey()).thenReturn("mocked-api-key");
        when(weatherbitFeignClient.get16DayForecast(any(), any())).thenReturn(weatherForecastResponse);

        // Then:
        Optional<WindsurfWeather> actualResponse = sut.getForecastForLocationAndDate("city1", LocalDate.now());
        assertTrue(actualResponse.isPresent());
        assertEquals(expectedWindsurfWeather, actualResponse.get());

        verify(feignClientConfiguration, times(1)).getApikey();
        verify(weatherbitFeignClient, times(1)).get16DayForecast("city1", "mocked-api-key");
    }

    @Test
    void getForecastForLocationAndDate_whenFeignThrowException_shouldReturnEmptyResponse() {
        // When:
        when(feignClientConfiguration.getApikey()).thenReturn("mocked-api-key");
        when(weatherbitFeignClient.get16DayForecast(any(), any())).thenThrow(RuntimeException.class);

        // Then:
        Optional<WindsurfWeather> actualResponse = sut.getForecastForLocationAndDate("city1", LocalDate.now());
        assertTrue(actualResponse.isEmpty());

        verify(feignClientConfiguration, times(1)).getApikey();
        verify(weatherbitFeignClient, times(1)).get16DayForecast("city1", "mocked-api-key");
    }
}