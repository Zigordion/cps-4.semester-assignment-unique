package cps.Repositories;

import cps.Repositories.Models.WeatherStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;

public interface WeatherStationRepository extends JpaRepository<WeatherStation,Long> {
    WeatherStation findFirstByStation(String stationName);
}
