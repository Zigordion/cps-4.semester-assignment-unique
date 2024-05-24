package cps.DataAccess.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "weatherStations")
public class WeatherStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String station;

    @Override
    public String toString() {
        return "WeatherStation{" +
                "id=" + id +
                ", station='" + station + '\'' +
                '}';
    }
}