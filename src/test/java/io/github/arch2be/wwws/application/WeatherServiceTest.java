package io.github.arch2be.wwws.application;

import io.github.arch2be.wwws.application.dto.City;
import io.github.arch2be.wwws.application.exceptions.WrongWindsurfDateException;
import io.github.arch2be.wwws.application.ports.WeatherForecast;
import io.github.arch2be.wwws.application.ports.WindsurfLocationRepository;
import io.github.arch2be.wwws.domain.WindsurfWeather;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WeatherForecast weatherForecast;

    @Mock
    private WindsurfLocationRepository windsurfLocationRepository;

    @InjectMocks
    private WeatherService sut;

    @Test
    void getBestWindsurfingLocation_whenPassWrongDateNotInRange_shouldThrowException() {
        // Given:
        final LocalDate wrongDate = LocalDate.now().plusDays(30);
        final LocalDate now = LocalDate.now();
        final LocalDate maxForecastDate = LocalDate.now().plusDays(15);

        // Then:
        assertThatThrownBy(() -> sut.getBestWindsurfingLocation(wrongDate))
                .isInstanceOf(WrongWindsurfDateException.class)
                .hasMessage(String.format("The date: [%s] is not in the range. Date should be within the range [%s] - [%s]", wrongDate, now, maxForecastDate));
    }

    @Test
    void getBestWindsurfingLocation_whenWeatherForecastServiceReturnNothing_shouldReturnEmptyResponse() {
        // Given:
        final LocalDate date = LocalDate.now();
        final String city = "city1";

        // When:
        when(windsurfLocationRepository.findAll()).thenReturn(List.of(new City(city)));
        when(weatherForecast.getForecastForLocationAndDate(any(), any())).thenReturn(Optional.empty());

        // Then:
        Optional<WindsurfWeather> actualResponse = sut.getBestWindsurfingLocation(date);
        assertTrue(actualResponse.isEmpty());

        verify(windsurfLocationRepository, times(1)).findAll();
        verify(weatherForecast, times(1)).getForecastForLocationAndDate(city, date);
    }

    @Test
    void getBestWindsurfingLocation_whenWeatherForecastServiceReturnResponsesButNotMeetWeatherConditions_shouldReturnEmptyResponse() {
        // Given:
        final LocalDate date = LocalDate.now();
        final String city = "city1";
        final WindsurfWeather expectedWindsurfWeather = new WindsurfWeather(city, 0.0, 0.0);


        // When:
        when(windsurfLocationRepository.findAll()).thenReturn(List.of(new City(city)));
        when(weatherForecast.getForecastForLocationAndDate(any(), any())).thenReturn(Optional.of(expectedWindsurfWeather));

        // Then:
        Optional<WindsurfWeather> actualResponse = sut.getBestWindsurfingLocation(date);
        assertTrue(actualResponse.isEmpty());

        verify(windsurfLocationRepository, times(1)).findAll();
        verify(weatherForecast, times(1)).getForecastForLocationAndDate(city, date);
    }

    @Test
    void getBestWindsurfingLocation_whenWeatherForecastServiceReturnCorrectResponses_shouldReturnResponseWithTheHighestScore() {
        // Given:
        final LocalDate date = LocalDate.now();
        final String city1 = "city1";
        final String city2 = "city2";
        final WindsurfWeather locationWithLowerScore = new WindsurfWeather(city1, 15.0, 10.0);
        final WindsurfWeather locationWithHigherScore = new WindsurfWeather(city2, 20.0, 10.0);

        // When:
        when(windsurfLocationRepository.findAll()).thenReturn(List.of(new City(city1), new City(city2)));
        when(weatherForecast.getForecastForLocationAndDate(any(), any()))
                .thenReturn(Optional.of(locationWithLowerScore))
                .thenReturn(Optional.of(locationWithHigherScore));

        // Then:
        Optional<WindsurfWeather> actualResponse = sut.getBestWindsurfingLocation(date);
        assertTrue(actualResponse.isPresent());
        assertEquals(locationWithHigherScore, actualResponse.get());

        verify(windsurfLocationRepository, times(1)).findAll();
        verify(weatherForecast, times(1)).getForecastForLocationAndDate(city1, date);
        verify(weatherForecast, times(1)).getForecastForLocationAndDate(city2, date);
    }
}