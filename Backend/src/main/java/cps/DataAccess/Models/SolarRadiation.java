package cps.DataAccess.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SolarRadiation implements IGraphDataType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double value;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "solarRad", orphanRemoval = true)
    private WeatherData weatherData;

}