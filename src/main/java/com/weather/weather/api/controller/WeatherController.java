package com.weather.weather.api.controller;

import com.weather.weather.api.model.WeatherData;
import com.weather.weather.api.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    /**
     * POST /api/weather/fetch
     */
    @PostMapping("/fetch")
    public ResponseEntity<WeatherData> fetchWeather(
            @RequestParam String dateTime,
            @RequestParam String lat,
            @RequestParam String lon,
            @RequestParam String cityName) {
        try {
            WeatherData data = service.fetchAndSaveWeatherMeteomatics(dateTime, lat, lon, cityName);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET /api/weather/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<WeatherData>> getAllWeather() {
        List<WeatherData> dados = service.getAllWeatherData();
        return ResponseEntity.ok(dados);
    }

    /**
     * GET /api/weather/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<WeatherData> getWeatherById(@PathVariable Long id) {
        WeatherData dado = service.getWeatherById(id);
        if (dado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dado);
    }
}
