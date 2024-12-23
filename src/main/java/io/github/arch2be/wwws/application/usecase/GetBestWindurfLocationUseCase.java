package io.github.arch2be.wwws.application.usecase;

import io.github.arch2be.wwws.domain.WindsurfWeather;

import java.time.LocalDate;
import java.util.Optional;

public interface GetBestWindurfLocationUseCase {

    Optional<WindsurfWeather> getBestWindsurfingLocation(LocalDate date);
}
