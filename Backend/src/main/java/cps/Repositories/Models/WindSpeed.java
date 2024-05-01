package cps.Repositories.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class WindSpeed implements IGraphDataType{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double value;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private WeatherData weatherData;

}