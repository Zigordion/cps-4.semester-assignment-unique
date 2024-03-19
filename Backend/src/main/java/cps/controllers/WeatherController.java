package cps.controllers;

import cps.models.WeatherData;
import cps.services.WeatherDBService;
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
    public String PrintTimeData(){
        return weatherDBService.getDateTime();
    }
    @PostMapping("/privateDoNotCall")
    public void StoreValueDB(){
        weatherDBService.storeValuesInDB();
    }
    @GetMapping("/")
    public WeatherData GetWeatherData(){
        return weatherDBService.getLatestValueFromDB();
    }

    @GetMapping("/{timestampInput}")
    public WeatherData GetWeatherDataSpecific(@PathVariable String timestampInput){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
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
