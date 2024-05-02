package cps.Repositories.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Temperature implements IGraphDataType{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double value;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "temperature", orphanRemoval = true)
    private WeatherData weatherData;

}