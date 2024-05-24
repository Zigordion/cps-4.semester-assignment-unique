package cps.BusinessLogic;

import cps.DataAccess.Models.WeatherData;
import cps.DataAccess.Models.WeatherStation;

interface IApiClient {
    WeatherData constructWeatherData(WeatherDataBuilder weatherDataBuilder, WeatherStation weatherStation);
}
