package com.assignment.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;


public class OpenWeatherMapRestClientTest {

    @Test
    public void testCityXXXNotFound() {
        OpenWeatherMapRestClient openWeatherMapRestClient = new OpenWeatherMapRestClient(new RestTemplate());
        Assertions.assertNull(openWeatherMapRestClient.getWeather("XXX"));
    }

    @Test
    public void testCityArnhemAndCountryNL() {
        OpenWeatherMapRestClient openWeatherMapRestClient = new OpenWeatherMapRestClient(new RestTemplate());
        Assertions.assertEquals("Gemeente Arnhem", openWeatherMapRestClient.getWeather("Arnhem").getName());
        Assertions.assertEquals("NL", openWeatherMapRestClient.getWeather("Arnhem").getSys().getCountry());
    }

}
