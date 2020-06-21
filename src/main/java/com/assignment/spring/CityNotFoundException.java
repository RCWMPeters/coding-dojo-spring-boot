package com.assignment.spring;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String city) {
        super("Could not find city " + city);
    }
}

