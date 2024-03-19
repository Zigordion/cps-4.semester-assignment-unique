package cps.controllers;

import cps.models.WeatherData;
import cps.services.WeatherDBService;
import org.springframework.web.bind.annotation.*;


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
    public WeatherData GetTempData(){
        return weatherDBService.getLatestValueFromDB();
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
