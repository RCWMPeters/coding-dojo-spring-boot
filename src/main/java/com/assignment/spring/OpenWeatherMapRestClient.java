package com.assignment.spring;

import com.assignment.spring.model.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class OpenWeatherMapRestClient {

    // FYI RestTemplate might become deprecated.
    private final RestTemplate restTemplate;

    @Autowired
    public OpenWeatherMapRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(String city) {
        String url = Constants.WEATHER_API_URL.replace("{city}", city).replace("{appid}", Constants.APP_ID);
        WeatherResponse weatherResponse = null;
        try {
            ResponseEntity<WeatherResponse> weatherResponseResponseEntity = restTemplate.getForEntity(url, WeatherResponse.class);
            if (weatherResponseResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
                weatherResponse = weatherResponseResponseEntity.getBody();
            } else {
                log.warn("Unable to return requested information, HTTP return status NOT OK");
            }
        } catch (Exception e) {
            log.warn("An exception occurred, unable to return requested information", e);
        }
        return weatherResponse;
    }

}
