package cps.Services;

import cps.Repositories.*;
import cps.Repositories.Models.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
@Component
public class WeatherDataBuilder {
    private WeatherData weatherData = new WeatherData();
    private final TemperatureRepository temperatureRepository;
    private final HumidityRepository humidityRepository;
    private final RainRepository rainRepository;
    private final SolarRadiationRepository solarRadiationRepository;
    private final SunMinRepository sunMinRepository;
    private final WindSpeedRepository windSpeedRepository;
    private final WindDirectionRepository windDirectionRepository;
    private final CloudCoverageRepository cloudCoverageRepository;
    public WeatherDataBuilder(TemperatureRepository temperatureRepository,
                              HumidityRepository humidityRepository,
                              RainRepository rainRepository,
                              SolarRadiationRepository solarRadiationRepository,
                              SunMinRepository sunMinRepository,
                              WindSpeedRepository windSpeedRepository,
                              WindDirectionRepository windDirectionRepository,
                              CloudCoverageRepository cloudCoverageRepository) {
        this.temperatureRepository = temperatureRepository;
        this.humidityRepository = humidityRepository;
        this.rainRepository = rainRepository;
        this.solarRadiationRepository = solarRadiationRepository;
        this.sunMinRepository = sunMinRepository;
        this.windSpeedRepository = windSpeedRepository;
        this.windDirectionRepository = windDirectionRepository;
        this.cloudCoverageRepository = cloudCoverageRepository;
    }
    public WeatherDataBuilder setWindSpeed(Double windSpeed) {
        if(windSpeed == null){
            return this;
        }
        WindSpeed ws = new WindSpeed();
        ws.setValue(windSpeed);
        windSpeedRepository.save(ws);
        weatherData.setWindSpeed(ws);
        return this;
    }

    public WeatherDataBuilder setTemperature(Double temperature) {
        if(temperature == null){
            return this;
        }
        Temperature temp = new Temperature();
        temp.setValue(temperature);
        temperatureRepository.save(temp);
        weatherData.setTemperature(temp);
        return this;
    }

    public WeatherDataBuilder setWindDirection(Double windDirection) {
        if(windDirection == null){
            return this;
        }
        WindDirection wd = new WindDirection();
        wd.setValue(windDirection);
        windDirectionRepository.save(wd);
        weatherData.setWindDirection(wd);
        return this;
    }

    public WeatherDataBuilder setSunMin(Double sunMin) {
        if(sunMin == null){
            return this;
        }
        SunPrTen sm = new SunPrTen();
        sm.setValue(sunMin);
        sunMinRepository.save(sm);
        weatherData.setSunMin(sm);
        return this;
    }

    public WeatherDataBuilder setCloudCoverage(Double cloudCoverage) {
        if(cloudCoverage == null){
            return this;
        }
        CloudCoverage cc = new CloudCoverage();
        cc.setValue(cloudCoverage);
        cloudCoverageRepository.save(cc);
        weatherData.setCloudCoverage(cc);
        return this;
    }

    public WeatherDataBuilder setHumidity(Double humidity) {
        if(humidity == null){
            return this;
        }
        Humidity h = new Humidity();
        h.setValue(humidity);
        humidityRepository.save(h);
        weatherData.setHumidity(h);
        return this;
    }

    public WeatherDataBuilder setRain(Double rain) {
        if(rain == null){
            return this;
        }
        Rain r = new Rain();
        r.setValue(rain);
        rainRepository.save(r);
        weatherData.setRain(r);
        return this;
    }

    public WeatherDataBuilder setSolarRad(Double solarRad) {
        if(solarRad == null){
            return this;
        }
        SolarRadiation sr = new SolarRadiation();
        sr.setValue(solarRad);
        solarRadiationRepository.save(sr);
        weatherData.setSolarRad(sr);
        return this;
    }

    public WeatherDataBuilder setTimestamp(Timestamp timestamp) {
        weatherData.setTimestamp(timestamp);
        return this;
    }

    public WeatherDataBuilder setWeatherStation(WeatherStation weatherStation) {
        weatherData.setWeatherStation(weatherStation);
        return this;
    }

    public WeatherData build() {
        WeatherData constructedWeatherData = weatherData;
        weatherData = new WeatherData();
        return constructedWeatherData;
    }
}
