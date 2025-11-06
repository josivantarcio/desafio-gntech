package com.weather.weather.api.service;

import com.weather.weather.api.model.WeatherData;
import com.weather.weather.api.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
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

    public WeatherData fetchAndSaveWeatherMeteomatics(String dateTime, String lat, String lon) {
        String url = String.format("%s/%s/t_2m:C/%s,%s/json?model=mix", meteomaticsUrl, dateTime, lat, lon);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(meteomaticsUser, meteomaticsPassword);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        double temp = 0.0;
        String cidade = lon + "," + lat; // personalize se quiser

        String description = response.getBody().toString();

        WeatherData data = new WeatherData(
                null,
                cidade,  // você pode customizar aqui
                temp,     // preencha após parsear
                null,     // umidade (não retorna por padrão, precisa outro parâmetro)
                description,
                LocalDateTime.now()
        );
        return repository.save(data);
    }
}
