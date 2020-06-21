package com.assignment.spring.entity;

import com.assignment.spring.WeatherResponseConverter;
import com.assignment.spring.model.WeatherResponse;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * This entity contains the full JSON reply from the WeatherMapReport service. On top of that it contains a few
 * attributes (city, name) which derive their values from the JSON reply, for which queries have been defined in the
 * WeatherRepository. More of those kind of attributes can be added later since the JSON reply is still available.
 */
@Entity
@Table(name = "weather")
@Data
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Name of the city as used in request to OpenWeatherMap. Might not be same as name.
    // For instance city = "arnhem", name = "Gemeente Arnhem"
    private String city;

    // Name of the city as used in response from OpenWeatherMap. Might not be same as city.
    // For instance city = "arnhem", name = "Gemeente Arnhem"
    private String name;

    // Seems to make sense to add this information.
    private LocalDateTime timeOfRequest;

    @Convert(converter = WeatherResponseConverter.class)
    @Column(length = 1000)
    private WeatherResponse weatherResponse;

}
