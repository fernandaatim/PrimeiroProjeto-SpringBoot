package com.berwald.api.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ApiServiceWeather {

    private static final int MAX_RETRIES = 3;

    public String getWeather(Double latitude, Double longitude) {
        if (latitude == null || longitude == null) {
            return "Digite um valor válido!";
        }

        String encodedLatitude = URLEncoder.encode(String.valueOf(latitude), StandardCharsets.UTF_8);
        String encodedLongitude = URLEncoder.encode(String.valueOf(longitude), StandardCharsets.UTF_8);
        String url = String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&hourly=temperature", encodedLatitude, encodedLongitude);

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();


        factory.setProxy(proxy);

        RestTemplate restTemplate = new RestTemplate(factory);

        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            try {
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    String responseBody = response.getBody();
                    return extractCurrentTemperature(responseBody);
                } else {
                    return "Erro na requisição: " + response.getStatusCode();
                }
            } catch (Exception e) {
                if (attempt == MAX_RETRIES - 1) {
                    return "Erro ao acessar a API: " + e.getMessage();
                }
            }
        }
        return "Erro desconhecido.";
    }

    private String extractCurrentTemperature(String jsonResponse) {
        JsonElement jsonElement = JsonParser.parseString(jsonResponse);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        // Acessando o primeiro horário da previsão
        JsonObject hourly = jsonObject.getAsJsonObject("hourly");
        JsonElement times = hourly.get("time");
        JsonElement temperatures = hourly.get("temperature");

        if (times != null && temperatures != null && times.isJsonArray() && temperatures.isJsonArray()) {
            // Obtendo o horário atual
            LocalDateTime now = LocalDateTime.now();
            String currentHour = now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).substring(0, 13) + ":00";

            // Percorrendo os horários para encontrar a temperatura correspondente
            for (int i = 0; i < times.getAsJsonArray().size(); i++) {
                if (times.getAsJsonArray().get(i).getAsString().equals(currentHour)) {
                    return "Temperatura atual: " + temperatures.getAsJsonArray().get(i).getAsString() + "°C";
                }
            }
        }

        return "Dados de temperatura não disponíveis.";
    }
}
