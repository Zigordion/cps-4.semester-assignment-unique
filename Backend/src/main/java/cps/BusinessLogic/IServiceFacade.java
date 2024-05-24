package cps.BusinessLogic;

import cps.Presentation.DTO.GraphDataDTO;
import cps.DataAccess.Models.WeatherData;
import cps.Util.Data.DataTypes;
import cps.Util.Data.SSETopic;
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
