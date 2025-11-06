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

    @PostMapping("/fetch")
    public WeatherData fetchWeather(@RequestParam String dateTime,
                                    @RequestParam String lat,
                                    @RequestParam String lon) {
        return service.fetchAndSaveWeatherMeteomatics(dateTime, lat, lon);
    }

    // busca todos os dados
    @GetMapping("/all")
    public ResponseEntity<List<WeatherData>> getAllWeather() {
        List<WeatherData> dados = weatherService.getAllWeatherData();
        return ResponseEntity.ok(dados);
    }

    // busca por id
    @GetMapping("/{id}")
    public ResponseEntity<WeatherData> getWeatherById(@PathVariable Long id) {
        WeatherData dado = weatherService.getWeatherById(id);
        if (dado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dado);
    }

}
