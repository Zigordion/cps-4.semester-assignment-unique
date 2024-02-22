package cps.services;

import cps.models.WeatherData;
import cps.repositories.WeatherDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;

@Service
public class WeatherDBService {
    WeatherDataRepository weatherDataRepository;
    public WeatherDBService(WeatherDataRepository weatherDataRepository){
        //Dependency injection. This repository is created automatically by spring
        this.weatherDataRepository = weatherDataRepository;
    }

    public void addWeatherToDB(WeatherData weatherData){
        //TMP method
        System.out.println(weatherData.getTmpValue());
        weatherDataRepository.save(weatherData);
        System.out.println(weatherDataRepository.findAll());
    }
    public WeatherData getWeatherData(Long id){
        WeatherData weatherData = weatherDataRepository.findById(id).orElse(new WeatherData());
        System.out.println(weatherData.getTmpValue());
        return weatherDataRepository.findById(id).orElse(null);
    }
// API key to dmi: 4ed024e9-49f9-4286-b988-5c97597774db
//4046b36a-f328-471f-91db-0249dbf703d3
    public void callExternalAPI(){
        URI uri = UriComponentsBuilder.fromUriString("https://dmigw.govcloud.dk/v1/forecastdata")
                .queryParam("api-key", "4ed024e9-49f9-4286-b988-5c97597774db")
                .build()
                .toUri();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try{
            response = HttpClient.newHttpClient().send(request,HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(response.body());
    }

}
