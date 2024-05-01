package cps.Controllers.DTO;


import cps.Repositories.Models.WeatherData;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class WeatherDataDTO {
    private String weatherStation;
    private Double temperature;
    private Double windSpeed;
    private Double windDirection;
    private Double sunMin;
    private Double cloudCoverage;
    private Double humidity;
    private Double rain;
    private Double solarRad;
    private Timestamp timestamp;

    public WeatherDataDTO(WeatherData weatherData) {
        this.weatherStation = weatherData.getWeatherStation().getStation();
        this.temperature = weatherData.getTemperature().getValue();
        this.windSpeed = weatherData.getWindSpeed().getValue();
        this.windDirection = weatherData.getWindDirection().getValue();
        this.sunMin = weatherData.getSunMin().getValue();
        this.cloudCoverage = weatherData.getCloudCoverage().getValue();
        this.humidity = weatherData.getHumidity().getValue();
        this.rain = weatherData.getRain().getValue();
        this.solarRad = weatherData.getSolarRad().getValue();
        this.timestamp = weatherData.getTimestamp();
    }
}
