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
    private double humidity;
    private double rain;
    private double solarRad;
    private Timestamp timestamp;

}
