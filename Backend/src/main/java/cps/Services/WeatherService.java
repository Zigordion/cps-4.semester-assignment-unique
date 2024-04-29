package cps.Services;

import cps.Repositories.Models.WeatherData;
import cps.Repositories.WeatherDataRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class WeatherService {
    WeatherDataRepository weatherDataRepository;
    private ApiClient apiClient;
    public WeatherService(WeatherDataRepository weatherDataRepository){
        this.weatherDataRepository = weatherDataRepository;
        apiClient = new DmiClient();

    }
    public HttpResponse<String> test(){
        ApiClient apiClient1 = new YrClient();
        HttpResponse<String> response = apiClient1.query();
        JSONObject test = new JSONObject(response.body()).getJSONObject("properties").getJSONArray("timeseries").getJSONObject(0).getJSONObject("data");
        System.out.println(test);
        return response;
    }

    @Scheduled(fixedRate = 600_000) //equal  1000 = 1 sec * 60*10 10 min.
    public void storeValuesInDB() {
        WeatherData latestWeatherData = getLatestValueFromDB();
        WeatherData weatherData = apiClient.constructWeatherData();
        Timestamp timestamp = weatherData.getTimestamp();
        if(latestWeatherData != null && timestamp.equals(latestWeatherData.getTimestamp())){
            System.out.println("Already exists in db; skipping");
            return;
        }
        weatherDataRepository.save(weatherData);
    }

    public WeatherData getLatestValueFromDB(){
        System.out.println(weatherDataRepository.findFirstByTimestampLessThanEqualOrderByTimestampDesc(Timestamp.valueOf(LocalDateTime.now())));
        return weatherDataRepository.findFirstByTimestampLessThanEqualOrderByTimestampDesc(Timestamp.valueOf(LocalDateTime.now()));
    }
    public WeatherData getValueFromTimestamp(Timestamp timestamp){
        return weatherDataRepository.findFirstByTimestampLessThanEqualOrderByTimestampDesc(timestamp);
    }



    public String getDateTime() {
        Timestamp ts = getLatestValueFromDB().getTimestamp();
        Date date = new Date(ts.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        return sdf.format(date);
    }

    public Timestamp[] getAllTimestamps(){
        return weatherDataRepository.findTimestamps();
    }


    public double calculateOverallWeather() {
        WeatherData wd = getLatestValueFromDB();
        System.out.println(wd.toString());
        if (wd.getRain() > 1) {
            return 1;
        } else if (wd.getWindSpeed() > 8) {
            return 2;
        } else if (wd.getCloudCoverage() > 60) {
            return 3;
        } else {
            return 4;
        }
    }
}
