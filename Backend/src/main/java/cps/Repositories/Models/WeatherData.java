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
    @OneToOne
    @JoinColumn(name = "temperature_id")
    private Temperature temperature;
    @OneToOne
    @JoinColumn(name = "wind_speed_id")
    private WindSpeed windSpeed;
    @OneToOne
    @JoinColumn(name = "wind_direction_id")
    private WindDirection windDirection;
    @OneToOne
    @JoinColumn(name = "sun_pr_ten_id")
    private SunPrTen sunMin;
    @OneToOne
    @JoinColumn(name = "cloud_coverage_id")
    private CloudCoverage cloudCoverage;
    @OneToOne
    @JoinColumn(name = "humidity_id")
    private Humidity humidity;
    @OneToOne
    @JoinColumn(name = "rain_id")
    private Rain rain;
    @OneToOne
    @JoinColumn(name = "solar_radiation_id")
    private SolarRadiation solarRad;
    private Timestamp timestamp;

    @Override
    public String toString() {
        return "WeatherData{" +
                "id=" + id +
                ", weatherStation=" + weatherStation.getStation() +
                ", timestamp=" + timestamp +
                '}';
    }
}
