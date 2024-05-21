package cps.Controllers;

import cps.Controllers.DTO.*;
import cps.Repositories.Models.WeatherData;
import cps.Services.IServiceFacade;
import cps.Services.SSETopic;
import cps.Services.Util.DataTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("api/weather")
public class WeatherController {
    private final IServiceFacade serviceFacade;
    private final Map<String, DataTypes> dataTypesMap = new HashMap<>();
    public WeatherController(IServiceFacade serviceFacade){
        this.serviceFacade = serviceFacade;
        dataTypesMap.put("temperature", DataTypes.TEMPERATURE);
        dataTypesMap.put("wind-speed", DataTypes.WIND_SPEED);
        dataTypesMap.put("sunshine", DataTypes.SUN_MIN);
        dataTypesMap.put("cloud-coverage", DataTypes.CLOUD_COVERAGE);
        dataTypesMap.put("humidity", DataTypes.HUMIDITY);
        dataTypesMap.put("rain", DataTypes.RAIN);
        dataTypesMap.put("solar-radiation", DataTypes.SOLAR_RADIATION);
    }
    @GetMapping("/time")
    public DateTimeDTO getTimeData(){
        DateTimeDTO dateTimeDTO = new DateTimeDTO();
        dateTimeDTO.setTimeStamp(serviceFacade.getLatestDateTime());
        return dateTimeDTO;
    }

    @GetMapping("/time/all")
    public TimeStampsDTO getAllTimeData(){
        TimeStampsDTO timeStampsDTO = new TimeStampsDTO();
        timeStampsDTO.setTimestamps(serviceFacade.getAllTimestamps());
        return timeStampsDTO;
    }

    @GetMapping("/")
    public WeatherDataDTO getWeatherData(){
        return new WeatherDataDTO(serviceFacade.getLatestWeatherDataFromDb());
    }
    @GetMapping("/time/{timestampInput}")
    public WeatherDataDTO getSpecificWeatherData(@PathVariable String timestampInput){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            System.out.println(timestampInput);
            Date date = simpleDateFormat.parse(timestampInput);
            Timestamp timestamp = new Timestamp(date.getTime());
            return new WeatherDataDTO(serviceFacade.getWeatherDataFromTimestamp(timestamp));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/overall/{timestampInput}")
    public OverallWeatherDataDTO getOverallWeather(@PathVariable String timestampInput){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            WeatherData weatherData;
            if(timestampInput.equals("undefined")){
                weatherData = serviceFacade.getLatestWeatherDataFromDb();
            }else {
                Date date = simpleDateFormat.parse(timestampInput);
                Timestamp timestamp = new Timestamp(date.getTime());
                weatherData = serviceFacade.getWeatherDataFromTimestamp(timestamp);
            }
            OverallWeatherDataDTO overallWeatherDataDTO = new OverallWeatherDataDTO();
            overallWeatherDataDTO.setWeatherValue(serviceFacade.calculateOverallWeather(weatherData));
            return overallWeatherDataDTO;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    @GetMapping("/graph/{value}")
    public List<GraphDataDTO> getData(@PathVariable String value){
        DataTypes dataType = dataTypesMap.get(value);
        if(dataType== null){
            System.out.println("ERROR: path variable: "+ value + " not recognized");
            return null;
        }
        return serviceFacade.getGraphData(dataType);
    }
    @GetMapping(path = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeToWeather() {
        return serviceFacade.listenToTopic(SSETopic.WEATHER_DATA);
    }

}
