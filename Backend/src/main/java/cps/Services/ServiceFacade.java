package cps.Services;

import cps.Controllers.DTO.GraphDataDTO;
import cps.Repositories.Models.WeatherData;
import cps.Services.Util.DataTypes;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ServiceFacade implements IServiceFacade {
    private final WeatherService weatherService;
    private final WeatherGraphService weatherGraphService;
    private final SseService sseService;

    public ServiceFacade(WeatherService weatherService, WeatherGraphService weatherGraphService, SseService sseService) {
        this.weatherService = weatherService;
        this.weatherGraphService = weatherGraphService;
        this.sseService = sseService;
    }

    @Override
    public Timestamp[] getAllTimestamps() {
        return weatherService.getAllTimestamps();
    }

    @Override
    public String getLatestDateTime() {
        return weatherService.getDateTime();
    }

    @Override
    public WeatherData getLatestWeatherDataFromDb() {
        return weatherService.getLatestValueFromDB();
    }

    @Override
    public WeatherData getWeatherDataFromTimestamp(Timestamp timestamp) {
        return weatherService.getValueFromTimestamp(timestamp);
    }

    @Override
    public double calculateOverallWeather(WeatherData weatherData) {
        return weatherService.calculateOverallWeather(weatherData);
    }

    @Override
    public List<GraphDataDTO> getGraphData(DataTypes dataType) {
        return weatherGraphService.getData(dataType);
    }

    @Override
    public SseEmitter listenToTopic(SSETopic topic) {
        SseEmitter emitter = new SseEmitter();
        sseService.addEmitter(topic, emitter);
        return emitter;
    }
}
