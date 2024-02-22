package cps.models;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "Weather")
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int tmpValue;


}
