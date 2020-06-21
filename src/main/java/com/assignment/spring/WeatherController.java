package com.assignment.spring;

import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.model.WeatherResponse;
import com.assignment.spring.repository.WeatherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("/weather")
@RestController
@Slf4j
public class WeatherController {

    private final OpenWeatherMapRestClient openWeatherMapRestClient;
    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherController(OpenWeatherMapRestClient openWeatherMapRestClient, WeatherRepository weatherRepository) {
        this.openWeatherMapRestClient = openWeatherMapRestClient;
        this.weatherRepository = weatherRepository;
    }

    @GetMapping("/report/{city}")
    public WeatherEntity weather(@PathVariable String city) {
        WeatherEntity weatherEntity;
        WeatherResponse weatherResponse = openWeatherMapRestClient.getWeather(city);
        if (weatherResponse != null) {
            weatherEntity = mapper(weatherResponse, city);
            weatherEntity = weatherRepository.save(weatherEntity);
        } else {
            throw new CityNotFoundException(city);
        }
        return weatherEntity;
    }

    @GetMapping("/count")
    public long countAll() {
        return weatherRepository.count();
    }

    @GetMapping("/countbycity/{city}")
    public long countByCity(@PathVariable String city) {
        return weatherRepository.countByCity(city);
    }

    @GetMapping("/countbyname/{name}")
    public long countByName(@PathVariable String name) {
        return weatherRepository.countByName(name);
    }

    private static WeatherEntity mapper(WeatherResponse weatherResponse, String city) {
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setName(weatherResponse.getName());
        weatherEntity.setWeatherResponse(weatherResponse);
        weatherEntity.setCity(city);
        weatherEntity.setTimeOfRequest(LocalDateTime.now());
        return weatherEntity;
    }
}
