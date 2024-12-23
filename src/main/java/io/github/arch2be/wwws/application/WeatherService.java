package io.github.arch2be.wwws.application;

import io.github.arch2be.wwws.application.dto.City;
import io.github.arch2be.wwws.application.exceptions.WrongWindsurfDateException;
import io.github.arch2be.wwws.application.ports.WeatherForecast;
import io.github.arch2be.wwws.application.ports.WindsurfLocationRepository;
import io.github.arch2be.wwws.application.usecase.GetBestWindurfLocationUseCase;
import io.github.arch2be.wwws.domain.WindsurfWeather;
import io.github.arch2be.wwws.domain.WindsurfWeatherSpecification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
class WeatherService implements GetBestWindurfLocationUseCase {
    private final WeatherForecast weatherForecast;
    private final WindsurfLocationRepository windsurfLocationRepository;
    private final WindsurfWeatherSpecification weatherSpecification = new WindsurfWeatherSpecification();

    public WeatherService(WeatherForecast weatherForecast, WindsurfLocationRepository windsurfLocationRepository) {
        this.weatherForecast = weatherForecast;
        this.windsurfLocationRepository = windsurfLocationRepository;
    }

    @Override
    public Optional<WindsurfWeather> getBestWindsurfingLocation(final LocalDate windsurfDate) {
        final LocalDate startDate = LocalDate.now();
        final LocalDate maxForecastDate = startDate.plusDays(15);

        if (windsurfDate.isBefore(startDate) || windsurfDate.isAfter(maxForecastDate)) {
            throw new WrongWindsurfDateException(format("The date: [%s] is not in the range. Date should be within the range [%s] - [%s]", windsurfDate, startDate, maxForecastDate));
        }

        final List<City> windsurfLocations = windsurfLocationRepository.findAll();

        return windsurfLocations
                .parallelStream()
                .map(city -> weatherForecast.getForecastForLocationAndDate(city.name(), windsurfDate)
                        .filter(weatherSpecification::isSatisfiedBy))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.comparingDouble(WindsurfWeather::getScore).reversed())
                .limit(1)
                .findFirst();
    }
}