package io.github.arch2be.wwws.domain;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WindsurfWeatherTest {

    @Test
    void createWindsurfWeather_whenPassCorrectDate_shouldReturnScore() {
        // Given:
        double expectedScore = 45.0;

        // Then:
        assertEquals(expectedScore, new WindsurfWeather("test", 15.0, 10.0).getScore());
    }

    @Test
    void createWindsurfWeather_whenPassZeroValues_shouldReturnZeroScore() {
        // Given:
        double expectedScore = 0.0;

        // Then:
        assertEquals(expectedScore, new WindsurfWeather("test", 0.0, 0.0).getScore());
    }

}
