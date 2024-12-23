package io.github.arch2be.wwws.framework.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherbit", url = "${feign.weatherbit.url}")
interface WeatherbitFeignClient {

    @GetMapping("/forecast/daily")
    WeatherForecastResponse get16DayForecast(@RequestParam("city") String city, @RequestParam("key") String apiKey);
}