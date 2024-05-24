package cps.Presentation.DTO;


import cps.DataAccess.Models.WeatherData;
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
        this.timestamp = weatherData.getTimestamp();
        this.temperature = weatherData.getTemperature() != null ? weatherData.getTemperature().getValue() : null;
        this.windSpeed = weatherData.getWindSpeed() != null ? weatherData.getWindSpeed().getValue() : null;
        this.windDirection = weatherData.getWindDirection() != null ? weatherData.getWindDirection().getValue() : null;
        this.sunMin = weatherData.getSunMin() != null ? weatherData.getSunMin().getValue() : null;
        this.cloudCoverage = weatherData.getCloudCoverage() != null ? weatherData.getCloudCoverage().getValue() : null;
        this.humidity = weatherData.getHumidity() != null ? weatherData.getHumidity().getValue() : null;
        this.rain = weatherData.getRain() != null ? weatherData.getRain().getValue() : null;
        this.solarRad = weatherData.getSolarRad() != null ? weatherData.getSolarRad().getValue() : null;
    }

}
