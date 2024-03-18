package cps.controllers;

import cps.models.WeatherData;
import cps.services.WeatherDBService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("api/weather")
public class WeatherController {
    private final WeatherDBService weatherDBService;

    public WeatherController(WeatherDBService weatherDBService) {
        this.weatherDBService = weatherDBService;
    }

    @GetMapping("/time")
    public String PrintTimeData(){
        //tmp method
        return weatherDBService.getDateTime();
    }

    @GetMapping("/temp")
    public double GetWeatherData(){
        return weatherDBService.getAllValues().getTemperature();
    }
    @PostMapping("/")
    public void addWeatherData(@RequestBody WeatherData weatherData){
        //Tmp Method
        weatherDBService.addWeatherToDB(weatherData);
    }

    @GetMapping("/{id}")
    public WeatherData getWeatherData(@PathVariable Long id){
        //Tmp Method
        return weatherDBService.getWeatherData(id);

    }
    @GetMapping("/overall")
    public double getOverallWeather(){
        return weatherDBService.getOverallWeather();
    }



}
