package cps.Services;

import cps.Controllers.DTO.GraphDataDTO;
import cps.Repositories.Models.WeatherData;
import cps.Services.Util.DataTypes;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.sql.Timestamp;
import java.util.List;

public interface IServiceFacade {
    Timestamp[] getAllTimestamps();
    String getLatestDateTime();
    WeatherData getLatestWeatherDataFromDb();
    WeatherData getWeatherDataFromTimestamp(Timestamp timestamp);
    double calculateOverallWeather(WeatherData weatherData);
    List<GraphDataDTO> getGraphData(DataTypes dataType);
    SseEmitter listenToTopic(SSETopic topic);

}
