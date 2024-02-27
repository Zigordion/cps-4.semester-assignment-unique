package cps.controllers;

import cps.models.WeatherData;
import cps.services.WeatherDBService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("api/weather")
public class WeatherController {
    private final WeatherDBService weatherDBService;

    public WeatherController(WeatherDBService weatherDBService) {
        this.weatherDBService = weatherDBService;
    }

    @GetMapping("/{id}")
    public WeatherData getWeatherData(@PathVariable Long id){
        //Tmp Method
        return weatherDBService.getWeatherData(id);
    }
    @GetMapping("/temp")
    public double PrintWeatherData(){
        //tmp method
        return weatherDBService.getTempFromExternalAPI();
    }
    @PostMapping("/")
    public void addWeatherData(@RequestBody WeatherData weatherData){
        //Tmp Method
        weatherDBService.addWeatherToDB(weatherData);
    }
}
