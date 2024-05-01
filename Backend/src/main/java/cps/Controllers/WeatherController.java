package cps.Controllers;

import cps.Controllers.DTO.*;
import cps.Services.Util.DataTypes;
import cps.Services.WeatherGraphService;
import cps.Services.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("api/weather")
public class WeatherController {
    private final WeatherService weatherService;
    private final WeatherGraphService weatherGraphService;
    private final Map<String, DataTypes> dataTypesMap = new HashMap<>();
    public WeatherController(WeatherService weatherService, WeatherGraphService weatherGraphService){
        this.weatherService = weatherService;
        this.weatherGraphService = weatherGraphService;
        dataTypesMap.put("temperature", DataTypes.TEMPERATURE);
        dataTypesMap.put("wind-speed", DataTypes.WIND_SPEED);
        dataTypesMap.put("sunshine", DataTypes.SUN_MIN);
        dataTypesMap.put("cloud-coverage", DataTypes.CLOUD_COVERAGE);
        dataTypesMap.put("humidity", DataTypes.HUMIDITY);
        dataTypesMap.put("rain", DataTypes.RAIN);
        dataTypesMap.put("solar-radiation", DataTypes.SOLAR_RADIATION);
    }
    @GetMapping("/time")
    public DateTimeDTO GetTimeData(){
        DateTimeDTO dateTimeDTO = new DateTimeDTO();
        dateTimeDTO.setTimeStamp(weatherService.getDateTime());
        return dateTimeDTO;
    }

    @GetMapping("/time/all")
    public TimeStampsDTO GetAllTimeData(){
        TimeStampsDTO timeStampsDTO = new TimeStampsDTO();
        timeStampsDTO.setTimestamps(weatherService.getAllTimestamps());
        return timeStampsDTO;
    }

    @GetMapping("/")
    public WeatherDataDTO GetWeatherData(){
        return new WeatherDataDTO(weatherService.getLatestValueFromDB());
    }
    @GetMapping("/time/{timestampInput}")
    public WeatherDataDTO GetWeatherDataSpecific(@PathVariable String timestampInput){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            Date date = simpleDateFormat.parse(timestampInput);
            Timestamp timestamp = new Timestamp(date.getTime());
            return new WeatherDataDTO(weatherService.getValueFromTimestamp(timestamp));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/overall")
    public OverallWeatherDataDTO getOverallWeather(){
        OverallWeatherDataDTO overallWeatherDataDTO = new OverallWeatherDataDTO();
        overallWeatherDataDTO.setWeatherValue(weatherService.calculateOverallWeather());
        return overallWeatherDataDTO;
    }

    @GetMapping("/graph/{value}")
    public List<GraphDataDTO> getData(@PathVariable String value){
        DataTypes dataType = dataTypesMap.get(value);
        if(dataType== null){
            System.out.println("ERROR: path variable: "+ value + " not recognized");
            return null;
        }
        return weatherGraphService.getData(dataType);
    }

}
