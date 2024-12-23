package io.github.arch2be.wwws.application.ports;

import io.github.arch2be.wwws.domain.WindsurfWeather;

import java.time.LocalDate;
import java.util.Optional;


public interface WeatherForecast {

    Optional<WindsurfWeather> getForecastForLocationAndDate(String location, LocalDate date);
}