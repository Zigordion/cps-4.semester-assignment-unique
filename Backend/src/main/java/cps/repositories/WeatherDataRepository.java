package cps.repositories;

import cps.models.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface WeatherDataRepository extends JpaRepository<WeatherData,Long> {
    @Query("SELECT wd FROM WeatherData wd ORDER BY wd.timestamp DESC")
    WeatherData findLatestEntry();
    @Query("SELECT wd FROM WeatherData wd WHERE wd.timestamp <= :timestamp ORDER BY wd.timestamp DESC")
    WeatherData findByClosestTimestamp(@Param("timestamp") Timestamp timestamp);
}
