package cps.DataAccess;

import cps.DataAccess.Models.WeatherStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherStationRepository extends JpaRepository<WeatherStation,Long> {
    WeatherStation findFirstByStation(String stationName);
}
