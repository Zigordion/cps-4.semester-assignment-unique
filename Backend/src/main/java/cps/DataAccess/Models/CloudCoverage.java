package cps.DataAccess.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CloudCoverage implements IGraphDataType{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double value;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cloudCoverage", orphanRemoval = true)
    private WeatherData weatherData;

}