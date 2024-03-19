package cps.repositories;

import cps.models.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface WeatherDataRepository extends JpaRepository<WeatherData,Long> {
    @Query("SELECT wd FROM WeatherData wd WHERE wd.timestamp = (SELECT MAX(wd2.timestamp) FROM WeatherData wd2)")
    WeatherData findLatestEntry();
    @Query("SELECT wd FROM WeatherData wd WHERE wd.timestamp <= :timestamp ORDER BY wd.timestamp DESC")
    WeatherData findByClosestTimestamp(@Param("timestamp") Timestamp timestamp);
    @Query("SELECT timestamp FROM WeatherData ORDER BY timestamp DESC")
    Timestamp[] findWeatherDataByTimestamp();
}
