package com.assignment.spring;

import com.assignment.spring.model.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.io.IOException;

@Slf4j
public class WeatherResponseConverter implements AttributeConverter<WeatherResponse, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(WeatherResponse weatherResponse) {
        String weatherResponseJson = null;
        try {
            weatherResponseJson = objectMapper.writeValueAsString(weatherResponse);
        } catch (final JsonProcessingException e) {
            log.error("JSON writing error", e);
        }

        return weatherResponseJson;
    }

    @Override
    public WeatherResponse convertToEntityAttribute(String weatherResponseJson) {
        WeatherResponse weatherResponse = null;
        try {
            weatherResponse = objectMapper.readValue(weatherResponseJson, WeatherResponse.class);
        } catch (final IOException e) {
            log.error("JSON reading error", e);
        }

        return weatherResponse;
    }

}