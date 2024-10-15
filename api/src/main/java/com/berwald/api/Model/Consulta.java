package com.berwald.api.Model;

import java.time.LocalDateTime;

public class Consulta {
    private LocalDateTime data;
    private Double latitude;
    private Double longitude;
    private String weatherData;

    public Consulta(LocalDateTime data, Double latitude, Double longitude, String weatherData) {
        this.data = data;
        this.latitude = latitude;
        this.longitude = longitude;
        this.weatherData = weatherData;
    }

    // Getters
    public LocalDateTime getData() {
        return data;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getWeatherData() {
        return weatherData;
    }

    // Sobrescrevendo o método toString
    @Override
    public String toString() {
        return "Consulta{" +
                "data=" + data +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", weatherData='" + weatherData + '\'' +
                '}';
    }
}
