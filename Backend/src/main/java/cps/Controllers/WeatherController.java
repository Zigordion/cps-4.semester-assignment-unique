package cps.Controllers;

import cps.Repositories.Models.WeatherData;
import cps.Repositories.WeatherDataRepository;
import cps.Services.WeatherService;
import cps.Services.WeatherStationType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
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


@RestController
@RequestMapping("api/weather")
public class WeatherController {
    WeatherService weatherService;
    public WeatherController(WeatherService weatherService){
        this.weatherService = weatherService;
    }
    @GetMapping("/time")
    public String GetTimeData(){
        return weatherService.getDateTime();
    }

    @GetMapping("/time/all")
    public ResponseEntity<Timestamp[]> GetAllTimeData(){
        ResponseEntity<Timestamp[]> response = ResponseEntity.ok(weatherService.getAllTimestamps());
        System.out.println(response);
        return response;
    }

    @GetMapping("/")
    public WeatherData GetWeatherData(){
        return weatherService.getLatestValueFromDB();
    }
    @GetMapping("/time/{timestampInput}")
    public WeatherData GetWeatherDataSpecific(@PathVariable String timestampInput){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            Date date = simpleDateFormat.parse(timestampInput);
            Timestamp timestamp = new Timestamp(date.getTime());
            return weatherService.getValueFromTimestamp(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("/test")
    public HttpResponse<String> GettestData(){
        return weatherService.test();
    }


    @GetMapping("/overall")
    public double getOverallWeather(){
        return weatherService.calculateOverallWeather();
    }

    @PostMapping("/station")
    public void setWeatherType(){
        weatherService.setWeatherStation(WeatherStationType.DMI);
    }


}
