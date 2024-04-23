package cps.Services;

import cps.Repositories.Models.WeatherData;
import cps.Repositories.WeatherDataRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
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
    private final WeatherDataRepository weatherDataRepository;
    private IWeatherStation weatherStation = new DmiWeatherStation();
    private ApplicationContext context;
    public WeatherService(WeatherDataRepository weatherDataRepository, ApplicationContext ctx){
        this.weatherDataRepository = weatherDataRepository;
        context = ctx;
    }

    @Scheduled(fixedRate = 600_000) //equal  1000 = 1 sec * 60*10 10 min.
    private void storeValuesInDB() {
        WeatherData latestWeatherData = getLatestValueFromDB();
        Timestamp timestamp = weatherStation.getTime();
        if(latestWeatherData != null && timestamp.equals(latestWeatherData.getTimestamp())){
            System.out.println("skipping");
            return;
        }
        WeatherData weatherData = weatherStation.getWeatherData();
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
    public void setWeatherStation(WeatherStationType weatherStationType){
        Map<String,Object> beans = context.getBeansWithAnnotation(WeatherStation.class);

        for (Object value : beans.values()) {
            if(value instanceof IWeatherStation weatherStationInterface){
                if(weatherStationInterface.getWeatherStationType() == weatherStationType){
                    weatherStation = weatherStationInterface;
                }
            }
        }
        weatherStation = new DmiWeatherStation();
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
