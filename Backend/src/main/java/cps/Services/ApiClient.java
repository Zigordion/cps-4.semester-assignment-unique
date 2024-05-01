package cps.Services;

import cps.Repositories.Models.WeatherData;
import cps.Repositories.Models.WeatherStation;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class ApiClient {
    protected URI uri;

    public HttpResponse<String> query(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    public abstract WeatherData constructWeatherData(WeatherDataBuilder weatherDataBuilder, WeatherStation weatherStation);
}
