package cps.BusinessLogic;

import cps.DataAccess.Models.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
@Component
class WeatherDataBuilder {
    private WeatherData weatherData = new WeatherData();
    private final DataBaseService dataBaseService;
    WeatherDataBuilder(DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
    }
    WeatherDataBuilder setWindSpeed(Double windSpeed) {
        if(windSpeed == null){
            return this;
        }
        WindSpeed ws = new WindSpeed();
        ws.setValue(windSpeed);
        dataBaseService.save(ws);
        weatherData.setWindSpeed(ws);
        return this;
    }

    WeatherDataBuilder setTemperature(Double temperature) {
        if(temperature == null){
            return this;
        }
        Temperature temp = new Temperature();
        temp.setValue(temperature);
        dataBaseService.save(temp);
        weatherData.setTemperature(temp);
        return this;
    }

    WeatherDataBuilder setWindDirection(Double windDirection) {
        if(windDirection == null){
            return this;
        }
        WindDirection wd = new WindDirection();
        wd.setValue(windDirection);
        dataBaseService.save(wd);
        weatherData.setWindDirection(wd);
        return this;
    }

    WeatherDataBuilder setSunMin(Double sunMin) {
        if(sunMin == null){
            return this;
        }
        SunPrTen sm = new SunPrTen();
        sm.setValue(sunMin);
        dataBaseService.save(sm);
        weatherData.setSunMin(sm);
        return this;
    }

    WeatherDataBuilder setCloudCoverage(Double cloudCoverage) {
        if(cloudCoverage == null){
            return this;
        }
        CloudCoverage cc = new CloudCoverage();
        cc.setValue(cloudCoverage);
        dataBaseService.save(cc);
        weatherData.setCloudCoverage(cc);
        return this;
    }

    WeatherDataBuilder setHumidity(Double humidity) {
        if(humidity == null){
            return this;
        }
        Humidity h = new Humidity();
        h.setValue(humidity);
        dataBaseService.save(h);
        weatherData.setHumidity(h);
        return this;
    }

    WeatherDataBuilder setRain(Double rain) {
        if(rain == null){
            return this;
        }
        Rain r = new Rain();
        r.setValue(rain);
        dataBaseService.save(r);
        weatherData.setRain(r);
        return this;
    }

    WeatherDataBuilder setSolarRad(Double solarRad) {
        if(solarRad == null){
            return this;
        }
        SolarRadiation sr = new SolarRadiation();
        sr.setValue(solarRad);
        dataBaseService.save(sr);
        weatherData.setSolarRad(sr);
        return this;
    }

    WeatherDataBuilder setTimestamp(Timestamp timestamp) {
        weatherData.setTimestamp(timestamp);
        return this;
    }

    WeatherDataBuilder setWeatherStation(WeatherStation weatherStation) {
        weatherData.setWeatherStation(weatherStation);
        return this;
    }

    WeatherData build() {
        WeatherData constructedWeatherData = weatherData;
        weatherData = new WeatherData();
        return constructedWeatherData;
    }
}
