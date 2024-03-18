package cps.services;

import cps.models.WeatherData;
import cps.repositories.WeatherDataRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;
import java.sql.Timestamp;

@Service
public class WeatherDBService {
    WeatherDataRepository weatherDataRepository;
    public WeatherDBService(WeatherDataRepository weatherDataRepository){
        //Dependency injection. This repository is created automatically by spring
        this.weatherDataRepository = weatherDataRepository;
    }

    public void addWeatherToDB(WeatherData weatherData){
        //TMP method
        weatherDataRepository.save(weatherData);
    }
    public WeatherData getWeatherData(Long id){
        return weatherDataRepository.findById(id).orElse(new WeatherData());
    }

    public WeatherData getCurrentWeatherData(){
        //if weather data already exists in database for the given time stamp then send that instead
        WeatherData weatherData = new WeatherData();
        weatherData.setTemperature(getTemperatureExternalAPI());
        weatherData.setWindSpeed(0);
        weatherData.setWindDirection(0);
        weatherData.setSunMin(0);
        weatherData.setCloudCoverage(0);
        weatherData.setHumidity(getHumidityExternalAPI());
        weatherData.setRain(0);
        weatherData.setSolarRad(0);
        weatherData.setTimestamp(new Timestamp(0));
        return weatherData;
    }
// API key to dmi: 4ed024e9-49f9-4286-b988-5c97597774db
//4046b36a-f328-471f-91db-0249dbf703d3
//        URI uri = UriComponentsBuilder.fromUriString("https://dmigw.govcloud.dk/v1/forecastdata")
//                .queryParam("api-key", "4ed024e9-49f9-4286-b988-5c97597774db")
//                .build()
//                .toUri();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(uri)
//                .GET()
//                .build();
    //https://dmigw.govcloud.dk/v2/metObs/collections/observation/items?period=latest-10-minutes&parameterId=temp_dry&bbox=9.938,55.2629,10.8478,55.6235&api-key=03c12439-02f5-45e5-92c1-8e633abfa088
    public double getTemperatureExternalAPI(){
        URI uri = constructURIForExternalAPI("temp_dry");
        return getValue(uri);
    }
    public double getHumidityExternalAPI(){
        URI uri = constructURIForExternalAPI("humidity");
        return getValue(uri);
    }
    private URI constructURIForExternalAPI(String parameterId){
        return UriComponentsBuilder.fromUriString("https://dmigw.govcloud.dk/v2/metObs/collections/observation/items")
                .queryParam("period","latest-10-minutes")
                .queryParam("parameterId",parameterId)
                .queryParam("bbox","9.938,55.2629,10.8478,55.6235")
                .queryParam("api-key","03c12439-02f5-45e5-92c1-8e633abfa088")
                .build()
                .toUri();
    }

    private double getValue(URI uri){
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
        return new JSONObject(response.body()).getJSONArray("features").getJSONObject(0).getJSONObject("properties").getDouble("value");
    }

}
