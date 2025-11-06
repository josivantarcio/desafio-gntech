package com.weather.weather.api.service;

import com.weather.weather.api.dto.MeteomaticsResponse;
import com.weather.weather.api.model.WeatherData;
import com.weather.weather.api.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {
    private final WeatherDataRepository repository;
    private final RestTemplate restTemplate;

    @Value("${meteomatics.api.user}")
    private String meteomaticsUser;
    @Value("${meteomatics.api.password}")
    private String meteomaticsPassword;
    @Value("${meteomatics.api.url}")
    private String meteomaticsUrl;

    public WeatherService(WeatherDataRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public List<WeatherData> getAllWeatherData() {
        return repository.findAll();
    }

    public WeatherData getWeatherById(Long id) {
        return repository.findById(id).orElse(null);
    }


    public WeatherData fetchAndSaveWeatherMeteomatics(String dateTime, String lat, String lon) {
        String url = String.format("%s/%s/t_2m:C/%s,%s/json?model=mix", meteomaticsUrl, dateTime, lat, lon);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(meteomaticsUser, meteomaticsPassword);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<MeteomaticsResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                MeteomaticsResponse.class);
        // Safe parse
        double temp = 0.0;
        String obsDate = null;
        String description = "Temperatura 2m (C)";
        MeteomaticsResponse body = response.getBody();
        if (body != null &&
                body.getData() != null &&
                !body.getData().isEmpty() &&
                body.getData().get(0).getCoordinates() != null &&
                !body.getData().get(0).getCoordinates().isEmpty() &&
                body.getData().get(0).getCoordinates().get(0).getDates() != null &&
                !body.getData().get(0).getCoordinates().get(0).getDates().isEmpty()) {
            temp = body.getData().get(0).getCoordinates().get(0).getDates().get(0).getValue();
            obsDate = body.getData().get(0).getCoordinates().get(0).getDates().get(0).getDate();
        }
        WeatherData data = new WeatherData(
                null,
                lat + "," + lon,
                temp,
                null, // umidade
                description,
                obsDate != null ? java.time.LocalDateTime.parse(obsDate.replace("Z", "")) : java.time.LocalDateTime.now()
        );
        return repository.save(data);
    }
}
