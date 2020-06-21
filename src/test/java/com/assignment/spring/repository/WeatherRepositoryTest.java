package com.assignment.spring.repository;

import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.model.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DataJpaTest
@TestPropertySource(locations = "classpath:application.yml")
public class WeatherRepositoryTest {

    private static final String WEATHER_RESPONSE_JSON_STRING = "{\n" +
            "  \"weatherResponse\": {\n" +
            "    \"coord\": {\n" +
            "      \"lon\": 5.9,\n" +
            "      \"lat\": 51.97\n" +
            "    },\n" +
            "    \"weather\": [\n" +
            "      {\n" +
            "        \"id\": 802,\n" +
            "        \"main\": \"Clouds\",\n" +
            "        \"description\": \"scattered clouds\",\n" +
            "        \"icon\": \"03d\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"base\": \"stations\",\n" +
            "    \"main\": {\n" +
            "      \"temp\": 294.32,\n" +
            "      \"pressure\": 1021,\n" +
            "      \"humidity\": 56,\n" +
            "      \"temp_min\": 293.15,\n" +
            "      \"temp_max\": 295.93,\n" +
            "      \"feels_like\": 292.78\n" +
            "    },\n" +
            "    \"visibility\": 10000,\n" +
            "    \"wind\": {\n" +
            "      \"speed\": 3.1,\n" +
            "      \"deg\": 0\n" +
            "    },\n" +
            "    \"clouds\": {\n" +
            "      \"all\": 40\n" +
            "    },\n" +
            "    \"dt\": 1592655450,\n" +
            "    \"sys\": {\n" +
            "      \"type\": 1,\n" +
            "      \"id\": 1526,\n" +
            "      \"country\": \"NL\",\n" +
            "      \"sunrise\": 1592622974,\n" +
            "      \"sunset\": 1592683188\n" +
            "    },\n" +
            "    \"id\": 2759660,\n" +
            "    \"name\": \"Gemeente Arnhem\",\n" +
            "    \"cod\": 200,\n" +
            "    \"timezone\": 7200\n" +
            "  }\n" +
            "}";

    @Autowired
    WeatherRepository weatherRepository;

    @Test
    public void testEmptyRepository() {
        assertEquals(0, weatherRepository.count());
    }

    @Test
    public void testSaveAndRetrieveWeatherEntity() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherResponse weatherResponse = objectMapper.readValue(WEATHER_RESPONSE_JSON_STRING, WeatherResponse.class);
            WeatherEntity weatherEntity = new WeatherEntity();
            weatherEntity.setCity("Arnhem");
            weatherEntity.setName(weatherResponse.getName());
            weatherEntity.setTimeOfRequest(LocalDateTime.now());
            weatherEntity.setWeatherResponse(weatherResponse);
            weatherEntity = weatherRepository.saveAndFlush(weatherEntity);

            assertEquals(weatherEntity, weatherRepository.findById(weatherEntity.getId()).orElse(null));

            // As a bonus, check if JSON string are the same.
            assertEquals(WEATHER_RESPONSE_JSON_STRING
                    .replaceAll("\\n\\s*", "")
                    .replaceAll(": ", ":"), objectMapper.writeValueAsString(weatherEntity.getWeatherResponse()));
        } catch (JsonProcessingException e) {
            fail(e);
        }
    }

}
