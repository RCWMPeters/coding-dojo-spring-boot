package com.assignment.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public OpenWeatherMapRestClient openWeatherMapRestClient() {
//        return new OpenWeatherMapRestClient(@Autowired RestTemplate);
//    }


}
