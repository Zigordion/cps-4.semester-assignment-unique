package cps.DataAccess;

import cps.DataAccess.Models.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface WeatherDataRepository extends JpaRepository<WeatherData,Long> {
//    @Query("SELECT wd FROM WeatherData wd WHERE wd.timestamp = (SELECT MAX(wd2.timestamp) FROM WeatherData wd2)")
//    WeatherData findLatestEntry();
    @Query("SELECT timestamp FROM WeatherData ORDER BY timestamp ASC")
    Timestamp[] findTimestamps();
    WeatherData findFirstByTimestampLessThanEqualOrderByTimestampDesc(@Param("timestamp") Timestamp timestamp);

}
