package io.github.arch2be.wwws.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class WindsurfWeatherSpecificationTest {

    private final WindsurfWeatherSpecification sut = new WindsurfWeatherSpecification();

    @Test
    void isSatisfiedBy_whenWeatherMeetCorrectCondition_shouldReturnTrue() {
        // Given:
        WindsurfWeather weather = new WindsurfWeather("test", 10, 10);

        // Then:
        assertTrue(sut.isSatisfiedBy(weather));
    }

    @Test
    void isSatisfiedBy_whenAvgTemperatureWeatherIsNotCorrect_shouldReturnFalse() {
        // Given:
        WindsurfWeather weather = new WindsurfWeather("test", 0, 10);

        // Then:
        assertFalse(sut.isSatisfiedBy(weather));
    }

    @Test
    void isSatisfiedBy_whenWindSpeedIsNotCorrect_shouldReturnFalse() {
        // Given:
        WindsurfWeather weather = new WindsurfWeather("test", 10, 0);

        // Then:
        assertFalse(sut.isSatisfiedBy(weather));
    }
}