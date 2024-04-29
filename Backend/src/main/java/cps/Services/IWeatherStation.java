package cps.Services;

import cps.Repositories.Models.WeatherData;

import java.sql.Timestamp;

@WeatherStation
public interface IWeatherStation {

    WeatherData getWeatherData();
    Timestamp getTime();
    WeatherStationType getWeatherStationType();
}
