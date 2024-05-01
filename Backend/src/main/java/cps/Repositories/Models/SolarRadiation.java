package cps.Repositories.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SolarRadiation implements IGraphDataType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double value;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "weather_data_id")
    private WeatherData weatherData;

}