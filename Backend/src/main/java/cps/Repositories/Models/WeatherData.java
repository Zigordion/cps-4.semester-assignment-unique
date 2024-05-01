package cps.Repositories.Models;
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
    @ManyToOne
    @JoinColumn(name = "weather_station_id")
    private WeatherStation weatherStation;
    @OneToOne(mappedBy = "weatherData")
    private Temperature temperature;
    @OneToOne(mappedBy = "weatherData")
    private WindSpeed windSpeed;
    @OneToOne(mappedBy = "weatherData")
    private WindDirection windDirection;
    @OneToOne(mappedBy = "weatherData")
    private SunPrTen sunMin;
    @OneToOne(mappedBy = "weatherData")
    private CloudCoverage cloudCoverage;
    @OneToOne(mappedBy = "weatherData")
    private Humidity humidity;
    @OneToOne(mappedBy = "weatherData")
    private Rain rain;
    @OneToOne(mappedBy = "weatherData")
    private SolarRadiation solarRad;
    private Timestamp timestamp;

    @Override
    public String toString() {
        return "WeatherData{" +
                "id=" + id +
                ", weatherStation=" + weatherStation.getStation() +
                ", temperature=" + temperature.getValue() +
                ", windSpeed=" + windSpeed.getValue() +
                ", windDirection=" + windDirection.getValue() +
                ", sunMin=" + sunMin.getValue() +
                ", cloudCoverage=" + cloudCoverage.getValue() +
                ", humidity=" + humidity.getValue() +
                ", rain=" + rain.getValue() +
                ", solarRad=" + solarRad.getValue() +
                ", timestamp=" + timestamp +
                '}';
    }
}
