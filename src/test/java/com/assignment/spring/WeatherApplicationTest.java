package com.assignment.spring;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WeatherApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
    }

    @Test
    @Order(value = 1) // Make sure this test runs first to get data in database.
    public void testReportFound() throws Exception {
        this.mockMvc.perform(get("/weather/report/arnhem")).andExpect(status().isOk())
                .andExpect(content().string(containsString("weatherResponse")));
        this.mockMvc.perform(get("/weather/report/arnhem")).andExpect(status().isOk())
                .andExpect(content().string(containsString("weatherResponse")));
        this.mockMvc.perform(get("/weather/report/nijmegen")).andExpect(status().isOk())
                .andExpect(content().string(containsString("weatherResponse")));
    }

    @Test
    public void testReportNotFound() throws Exception {
        this.mockMvc.perform(get("/weather/report/XXX")).andExpect(status().isNotFound());
    }

    @Test
    public void testGetCount() throws Exception {
        this.mockMvc.perform(get("/weather/count")).andExpect(status().isOk())
                .andExpect(content().string(containsString("3")));
    }

    @Test
    public void testGetCountByCity() throws Exception {
        this.mockMvc.perform(get("/weather/countbycity/arnhem")).andExpect(status().isOk())
                .andExpect(content().string(containsString("2")));
        this.mockMvc.perform(get("/weather/countbycity/nijmegen")).andExpect(status().isOk())
                .andExpect(content().string(containsString("1")));
    }

    @Test
    public void testGetCountByName() throws Exception {
        this.mockMvc.perform(get("/weather/countbyname/arnhem")).andExpect(status().isOk())
                .andExpect(content().string(containsString("0")));
        this.mockMvc.perform(get("/weather/countbyname/Gemeente Arnhem")).andExpect(status().isOk())
                .andExpect(content().string(containsString("2")));
        this.mockMvc.perform(get("/weather/countbyname/nijmegen")).andExpect(status().isOk())
                .andExpect(content().string(containsString("0")));
        this.mockMvc.perform(get("/weather/countbyname/Gemeente Nijmegen")).andExpect(status().isOk())
                .andExpect(content().string(containsString("1"))); // TODO test this in browser (spaces!)
    }

}


