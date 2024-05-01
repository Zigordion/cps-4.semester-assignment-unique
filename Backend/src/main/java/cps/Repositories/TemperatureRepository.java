package cps.Repositories;

import cps.Repositories.Models.Temperature;
import cps.Repositories.Models.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface TemperatureRepository extends JpaRepository<Temperature,Long> {
}
