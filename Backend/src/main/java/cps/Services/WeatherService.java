package cps.Services;

import cps.Repositories.Models.WeatherData;
import cps.Repositories.Models.WeatherStation;
import cps.Repositories.WeatherDataRepository;
import cps.Repositories.WeatherStationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class WeatherService {
    private final WeatherDataRepository weatherDataRepository;
    private final WeatherStationRepository weatherStationRepository;
    private final DataBuilder dataBuilder;
    private ApiClient apiClient;

    public WeatherService(WeatherDataRepository weatherDataRepository, WeatherStationRepository weatherStationRepository, DataBuilder dataBuilder) {
        this.weatherDataRepository = weatherDataRepository;
        this.weatherStationRepository = weatherStationRepository;
        this.dataBuilder = dataBuilder;
        apiClient = new DmiClient();
        //alternatively use a data seeder/SQL
        if (weatherStationRepository.count() == 0) {
            WeatherStation weatherStation = new WeatherStation();
            weatherStation.setStation("DMI");
            weatherStationRepository.save(weatherStation);
        }
    }

    @Scheduled(fixedRate = 600_000) //equal  1000 = 1 sec * 60*10 10 min.
    public void storeValuesInDB() {
        WeatherData latestWeatherData = getLatestValueFromDB();

        Timestamp timestamp = apiClient.getTimestamp();
        if (latestWeatherData != null && timestamp.equals(latestWeatherData.getTimestamp())) {
            System.out.println("Already exists in db; skipping");
            return;
        }
        WeatherStation weatherStation = weatherStationRepository.findFirstByStation("DMI");
        System.out.println(weatherStation);
        WeatherData weatherData = apiClient.constructWeatherData(dataBuilder, weatherStation, timestamp);
        weatherDataRepository.save(weatherData);
    }

    public WeatherData getLatestValueFromDB() {
        return weatherDataRepository.findFirstByTimestampLessThanEqualOrderByTimestampDesc(Timestamp.valueOf(LocalDateTime.now()));
    }

    public WeatherData getValueFromTimestamp(Timestamp timestamp) {
        return weatherDataRepository.findFirstByTimestampLessThanEqualOrderByTimestampDesc(timestamp);
    }

    public String getDateTime() {
        Timestamp ts = getLatestValueFromDB().getTimestamp();
        Date date = new Date(ts.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        return sdf.format(date);
    }

    public Timestamp[] getAllTimestamps() {
        return weatherDataRepository.findTimestamps();
    }

    public double calculateOverallWeather() {
        WeatherData wd = getLatestValueFromDB();
        System.out.println(wd.toString());
        if (wd.getRain().getValue() != null && wd.getRain().getValue() > 1) {
            return 1;
        } else if (wd.getWindSpeed().getValue() != null && wd.getWindSpeed().getValue() > 8) {
            return 2;
        } else if (wd.getCloudCoverage().getValue() != null && wd.getCloudCoverage().getValue() > 60) {
            return 3;
        } else {
            return 4;
        }
    }
}
