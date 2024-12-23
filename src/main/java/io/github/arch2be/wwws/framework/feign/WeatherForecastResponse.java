package io.github.arch2be.wwws.framework.feign;

import java.time.LocalDate;
import java.util.List;

class WeatherForecastResponse {

    private List<Forecast> data;

    public List<Forecast> getData() {
        return data;
    }

    public void setData(List<Forecast> data) {
        this.data = data;
    }

    static class Forecast {
        private LocalDate datetime;
        private double temp;
        private double maxTemp;
        private double minTemp;
        private double wind_spd;

        public LocalDate getDatetime() {
            return datetime;
        }

        public void setDatetime(LocalDate datetime) {
            this.datetime = datetime;
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getMaxTemp() {
            return maxTemp;
        }

        public void setMaxTemp(double maxTemp) {
            this.maxTemp = maxTemp;
        }

        public double getMinTemp() {
            return minTemp;
        }

        public void setMinTemp(double minTemp) {
            this.minTemp = minTemp;
        }

        public double getWind_spd() {
            return wind_spd;
        }

        public void setWind_spd(double wind_spd) {
            this.wind_spd = wind_spd;
        }
    }
}