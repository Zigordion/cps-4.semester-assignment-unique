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
