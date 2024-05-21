package cps.Services;

import cps.Controllers.DTO.WeatherDataDTO;
import cps.Repositories.Models.WeatherData;
import cps.Repositories.Models.WeatherStation;
import cps.Repositories.WeatherDataRepository;
import cps.Repositories.WeatherStationRepository;
import cps.Services.Util.SSETopic;
import cps.Services.Util.Util;
import cps.Services.Util.WeatherStations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
class WeatherService {
    private final WeatherDataRepository weatherDataRepository;
    private final WeatherStationRepository weatherStationRepository;
    private final WeatherDataBuilder weatherDataBuilder;
    private final SseService sseService;

    private final IApiClient IApiClient;

    WeatherService(WeatherDataRepository weatherDataRepository, WeatherStationRepository weatherStationRepository, WeatherDataBuilder weatherDataBuilder, SseService sseService) {
        this.weatherDataRepository = weatherDataRepository;
        this.weatherStationRepository = weatherStationRepository;
        this.weatherDataBuilder = weatherDataBuilder;
        this.sseService = sseService;
        IApiClient = new DmiClient();
        //alternatively use a data seeder/SQL
        if (weatherStationRepository.count() == 0) {
            WeatherStation weatherStation = new WeatherStation();
            weatherStation.setStation(WeatherStations.DMI.name());
            weatherStationRepository.save(weatherStation);
        }
    }

    @Scheduled(fixedRate = 600_000) //equal  1000 = 1 sec * 60*10 10 min.
    void fetchAndStoreValuesInDB() {
        WeatherData latestWeatherData = getLatestValueFromDB();
        WeatherStation weatherStation = weatherStationRepository.findFirstByStation(WeatherStations.DMI.name());
        System.out.println(weatherStation);
        WeatherData weatherData = IApiClient.constructWeatherData(weatherDataBuilder, weatherStation);
        Timestamp timestamp = weatherData.getTimestamp();
        if (latestWeatherData != null && timestamp.equals(latestWeatherData.getTimestamp())) {
            System.out.println("Already exists in db; skipping");
            return;
        }
        weatherDataRepository.save(weatherData);
        sseService.sendToClients(SSETopic.WEATHER_DATA,new WeatherDataDTO(weatherData));
    }

    WeatherData getLatestValueFromDB() {
        return weatherDataRepository.findFirstByTimestampLessThanEqualOrderByTimestampDesc(Timestamp.valueOf(LocalDateTime.now()));
    }

    WeatherData getValueFromTimestamp(Timestamp timestamp) {
        return weatherDataRepository.findFirstByTimestampLessThanEqualOrderByTimestampDesc(timestamp);
    }

    String getDateTime() {
        Timestamp ts = getLatestValueFromDB().getTimestamp();
        return Util.getDateTime(ts);
    }

    Timestamp[] getAllTimestamps() {
        return weatherDataRepository.findTimestamps();
    }

    double calculateOverallWeather(WeatherData wd) {
        if (wd.getRain() != null && wd.getRain().getValue() > 1) {
            return 1;
        } else if (wd.getWindSpeed() != null && wd.getWindSpeed().getValue() > 8) {
            return 2;
        } else if (wd.getCloudCoverage() != null && wd.getCloudCoverage().getValue() > 60) {
            return 3;
        } else {
            return 4;
        }
    }
}
