package io.github.arch2be.wwws.framework.api;

import io.github.arch2be.wwws.application.usecase.GetBestWindurfLocationUseCase;
import io.github.arch2be.wwws.domain.WindsurfWeather;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping(value = "/windsurf-location")
class GetWindsurfLocationController {

    private final GetBestWindurfLocationUseCase getBestWindsurfingLocation;

    GetWindsurfLocationController(GetBestWindurfLocationUseCase getBestWindsurfingLocation) {
        this.getBestWindsurfingLocation = getBestWindsurfingLocation;
    }

    @GetMapping
    ResponseEntity<?> getTheBestWindsurfLocationConditionForDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Optional<WindsurfWeather> maybeWindsurfWeather = getBestWindsurfingLocation.getBestWindsurfingLocation(date);

        if (maybeWindsurfWeather.isPresent()) {
            return ResponseEntity.ok(maybeWindsurfWeather
                    .map(windsurfWeather -> new WindsurfWeatherResponse(windsurfWeather.getLocation(), windsurfWeather.getAvgTemp(), windsurfWeather.getWindSpeed()))
            );
        }

        return ResponseEntity.ok("No suitable windsurfing location found for the given date.");
    }
}
