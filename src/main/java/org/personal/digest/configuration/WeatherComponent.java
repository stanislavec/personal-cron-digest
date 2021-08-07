package org.personal.digest.configuration;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WeatherComponent {

    private final WeatherConfuguration weatherConfuguration;

    public String getWeather() throws JSONException {

        String uri = "https://api.weatherbit.io/v2.0/current?city=Moscow&country=Russia&units=M&lang=ru&key=" + weatherConfuguration.getApiKey();

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        WeatherMessagesResponse response = Objects.requireNonNull(restTemplate.exchange(uri, HttpMethod.GET, null, WeatherResponse.class).getBody()).getData().get(0);

        String message = "\uD83C\uDF21️ Температура: " + response.getTemp() + "\n";
        message += "\uD83D\uDCA8 Ветер: " + response.getWind_cdir_full() + response.getWind_spd() + "м/с \n";
        message += "\uD83D\uDC86\u200D♀️ Давление: " + response.getPres() + "рт.ст. \n";
        message += "☁️ Облачность: " + response.getClouds() + "% \n";

        return message;
    }

    @Data
    static class WeatherResponse {
        ArrayList<WeatherMessagesResponse> data;
    }

    @Data
    static class WeatherMessagesResponse {
        Float temp;
        Float pres;
        Integer wind_spd;
        Integer clouds;
        String wind_cdir_full;
    }
}
