package com.weather.weather.api.repository;

import com.weather.weather.api.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    WeatherData findFirstByCityNameOrderByObservationTimeDesc(String cityName);
}
