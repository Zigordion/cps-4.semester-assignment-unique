package cps.BusinessLogic;

import cps.Presentation.DTO.GraphDataDTO;
import cps.DataAccess.Models.WeatherData;
import cps.Util.Data.DataTypes;
import cps.Util.Data.SSETopic;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.sql.Timestamp;
import java.util.List;

@Service
class ServiceFacade implements IServiceFacade {
    private final WeatherService weatherService;
    private final WeatherGraphService weatherGraphService;
    private final DataBaseService dataBaseService;
    private final SseService sseService;

    ServiceFacade(WeatherService weatherService, WeatherGraphService weatherGraphService, DataBaseService dataBaseService, SseService sseService) {
        this.weatherService = weatherService;
        this.weatherGraphService = weatherGraphService;
        this.dataBaseService = dataBaseService;

        this.sseService = sseService;
    }

    @Override
    public Timestamp[] getAllTimestamps() {
        return dataBaseService.getAllTimestamps();
    }

    @Override
    public String getLatestDateTime() {
        return weatherService.getDateTime();
    }

    @Override
    public WeatherData getLatestWeatherDataFromDb() {
        return dataBaseService.getLatestValueFromDB();
    }

    @Override
    public WeatherData getWeatherDataFromTimestamp(Timestamp timestamp) {
        return dataBaseService.getValueFromTimestamp(timestamp);
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
