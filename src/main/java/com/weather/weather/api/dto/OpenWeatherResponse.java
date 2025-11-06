package com.weather.weather.api.dto;

import lombok.Data;
import java.util.Map;

@Data
public class OpenWeatherResponse {
    private Map<String, Object> main;  // contém temperature, humidity
    private java.util.List<Map<String, Object>> weather; // contém description
}
