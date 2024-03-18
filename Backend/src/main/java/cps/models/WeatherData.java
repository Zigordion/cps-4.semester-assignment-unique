package cps.models;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "Weather")
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double temperature;
    private double windSpeed;
    private double windDirection;
    private double sunMin;
    private double cloudCoverage;

    public WeatherData(double temperature, double windSpeed, double windDirection, double sunMin, double cloudCoverage, double humidity, double rain, double solarRad, Timestamp timestamp) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.sunMin = sunMin;
        this.cloudCoverage = cloudCoverage;
        this.humidity = humidity;
        this.rain = rain;
        this.solarRad = solarRad;
        this.timestamp = timestamp;
    }

    private double humidity;
    private double rain;
    private double solarRad;
    private Timestamp timestamp;

}
