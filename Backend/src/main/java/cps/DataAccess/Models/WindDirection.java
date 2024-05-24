package cps.DataAccess.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class WindDirection implements IGraphDataType{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double value;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "windDirection", orphanRemoval = true)
    private WeatherData weatherData;

}