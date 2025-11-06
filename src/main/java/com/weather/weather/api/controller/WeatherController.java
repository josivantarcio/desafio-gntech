package com.weather.weather.api.controller;

import com.weather.weather.api.model.WeatherData;
import com.weather.weather.api.service.WeatherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @PostMapping("/fetch")
    public WeatherData fetchWeather(@RequestParam String dateTime,
                                    @RequestParam String lat,
                                    @RequestParam String lon) {
        return service.fetchAndSaveWeatherMeteomatics(dateTime, lat, lon);
    }
}
