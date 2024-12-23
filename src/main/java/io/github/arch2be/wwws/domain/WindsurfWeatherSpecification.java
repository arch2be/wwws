package io.github.arch2be.wwws.domain;

public class WindsurfWeatherSpecification {

    public boolean isSatisfiedBy(WindsurfWeather windsurfWeather) {
        return windsurfWeather.getWindSpeed() >= 5 && windsurfWeather.getWindSpeed() <= 18 &&
                windsurfWeather.getAvgTemp() >= 5 && windsurfWeather.getAvgTemp() <= 35;
    }
}
