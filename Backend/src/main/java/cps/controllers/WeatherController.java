package cps.controllers;

import cps.models.WeatherData;
import cps.services.WeatherDBService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class WeatherController {
    private final WeatherDBService weatherDBService;

    public WeatherController(WeatherDBService weatherDBService) {
        this.weatherDBService = weatherDBService;
    }

    @GetMapping("/weather/{id}")
    public WeatherData getWeatherData(@PathVariable Long id){
        //Tmp Method
        return weatherDBService.getWeatherData(id);
    }
    @GetMapping("/weather")
    public void PrintWeatherData(){
        //tmp method
        weatherDBService.callExternalAPI();
    }
    @PostMapping("/weather")
    public void addWeatherData(@RequestBody WeatherData weatherData){
        //Tmp Method
        weatherDBService.addWeatherToDB(weatherData);
    }
}
