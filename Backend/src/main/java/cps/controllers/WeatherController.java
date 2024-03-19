package cps.controllers;

import cps.models.WeatherData;
import cps.services.WeatherDBService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("api/weather")
public class WeatherController {
    private final WeatherDBService weatherDBService;

    public WeatherController(WeatherDBService weatherDBService) {
        this.weatherDBService = weatherDBService;
    }

    @GetMapping("/time")
    public String GetTimeData(){
        return weatherDBService.getDateTime();
    }
    @GetMapping("/time/all")
    public ResponseEntity<Timestamp[]> GetAllTimeData(){
        ResponseEntity<Timestamp[]> response = ResponseEntity.ok(weatherDBService.getAllTimestamps());
        System.out.println(response);
        return response;
    }

    @PostMapping("/privateDoNotCall")
    public void StoreValueDB(){
        weatherDBService.storeValuesInDB();
    }
    @GetMapping("/")
    public WeatherData GetWeatherData(){
        return weatherDBService.getLatestValueFromDB();
    }

    @GetMapping("/time/{timestampInput}")
    public WeatherData GetWeatherDataSpecific(@PathVariable String timestampInput){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        try {
            Date date = simpleDateFormat.parse(timestampInput);
            Timestamp timestamp = new Timestamp(date.getTime());
            return weatherDBService.getValueFromTimestamp(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("/overall")
    public double getOverallWeather(){
        return weatherDBService.getOverallWeather();
    }
}
