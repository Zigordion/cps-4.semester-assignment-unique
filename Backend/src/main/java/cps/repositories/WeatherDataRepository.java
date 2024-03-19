package cps.repositories;

import cps.models.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeatherDataRepository extends JpaRepository<WeatherData,Long> {
    @Query("SELECT wd FROM WeatherData wd ORDER BY wd.timestamp DESC")
    WeatherData findLatestEntry();
}
