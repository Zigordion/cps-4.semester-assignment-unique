package cps.Services;

import cps.Repositories.Models.WeatherData;
import cps.Repositories.Models.WeatherStation;

public interface IApiClient {
    WeatherData constructWeatherData(WeatherDataBuilder weatherDataBuilder, WeatherStation weatherStation);
}
