package io.github.arch2be.wwws.framework.feign;

import io.github.arch2be.wwws.application.ports.WeatherForecast;
import io.github.arch2be.wwws.domain.WindsurfWeather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

import static java.lang.String.format;

@Component
class WeatherForecastImpl implements WeatherForecast {

    private static final Logger log = LoggerFactory.getLogger(WeatherForecastImpl.class);
    private final FeignClientConfiguration feignClientConfiguration;
    private final WeatherbitFeignClient weatherbitFeignClient;

    public WeatherForecastImpl(WeatherbitFeignClient weatherbitFeignClient, FeignClientConfiguration feignClientConfiguration) {
        this.weatherbitFeignClient = weatherbitFeignClient;
        this.feignClientConfiguration = feignClientConfiguration;
    }

    @Override
    public Optional<WindsurfWeather> getForecastForLocationAndDate(String city, LocalDate date) {
        try {
            final WeatherForecastResponse weatherbitFeignClient16DayForecast = weatherbitFeignClient.get16DayForecast(city, feignClientConfiguration.getApikey());
            return weatherbitFeignClient16DayForecast.getData().stream()
                    .filter(forecast -> forecast.getDatetime().equals(date))
                    .findFirst()
                    .map(forecast -> new WindsurfWeather(city, forecast.getTemp(), forecast.getWind_spd()));
        } catch (Exception ex) {
            log.error(format("Error during call get16DayForecast() for city: [%s] and date: [%s]", city, date), ex);
            return Optional.empty();
        }
    }
}
