package cps.Services;

import cps.Repositories.*;
import cps.Repositories.Models.*;
import cps.Services.Util.WeatherStations;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
@RequiredArgsConstructor
@Service
class DataBaseService {
    private final WeatherDataRepository weatherDataRepository;
    private final WeatherStationRepository weatherStationRepository;
    private final TemperatureRepository temperatureRepository;
    private final HumidityRepository humidityRepository;
    private final RainRepository rainRepository;
    private final SolarRadiationRepository solarRadiationRepository;
    private final SunMinRepository sunMinRepository;
    private final WindSpeedRepository windSpeedRepository;
    private final WindDirectionRepository windDirectionRepository;
    private final CloudCoverageRepository cloudCoverageRepository;
    @PostConstruct
    void seedDataBase() {
        //alternatively use a data seeder/SQL
        if (weatherStationRepository.count() == 0) {
            WeatherStation weatherStation = new WeatherStation();
            weatherStation.setStation(WeatherStations.DMI.name());
            weatherStationRepository.save(weatherStation);
        }
    }

    void save(WeatherData weatherData) {
        weatherDataRepository.save(weatherData);
    }
    void save(WeatherStation weatherStation) {
        weatherStationRepository.save(weatherStation);
    }
    void save(Temperature temperature) {
        temperatureRepository.save(temperature);
    }
    void save(Humidity humidity) {
        humidityRepository.save(humidity);
    }
    void save(Rain rain) {
        rainRepository.save(rain);
    }
    void save(SolarRadiation solarRadiation) {
        solarRadiationRepository.save(solarRadiation);
    }
    void save(WindSpeed windSpeed) {
        windSpeedRepository.save(windSpeed);
    }
    void save(WindDirection windDirection) {
        windDirectionRepository.save(windDirection);
    }
    void save(CloudCoverage cloudCoverage) {
        cloudCoverageRepository.save(cloudCoverage);
    }
    void save(SunPrTen sunPrTen){
        sunMinRepository.save(sunPrTen);
    }


    WeatherData getLatestValueFromDB() {
        return weatherDataRepository.findFirstByTimestampLessThanEqualOrderByTimestampDesc(Timestamp.valueOf(LocalDateTime.now()));
    }

    WeatherData getValueFromTimestamp(Timestamp timestamp) {
        return weatherDataRepository.findFirstByTimestampLessThanEqualOrderByTimestampDesc(timestamp);
    }

    WeatherStation getWeatherStationFromName(String name) {
        return weatherStationRepository.findFirstByStation(name);
    }
    Timestamp[] getAllTimestamps() {
        return weatherDataRepository.findTimestamps();
    }
}
