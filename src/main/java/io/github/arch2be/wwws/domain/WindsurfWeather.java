package io.github.arch2be.wwws.domain;

import java.util.Objects;

public class WindsurfWeather {
    private final String location;
    private final double avgTemp;
    private final double windSpeed;
    private final double score;

    public WindsurfWeather(String location, double avgTemp, double windSpeed) {
        this.location = location;
        this.avgTemp = avgTemp;
        this.windSpeed = windSpeed;
        this.score = windSpeed * 3 + avgTemp;
    }

    public String getLocation() {
        return location;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WindsurfWeather weather = (WindsurfWeather) o;
        return Double.compare(avgTemp, weather.avgTemp) == 0 && Double.compare(windSpeed, weather.windSpeed) == 0 && Double.compare(score, weather.score) == 0 && Objects.equals(location, weather.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, avgTemp, windSpeed, score);
    }
}
