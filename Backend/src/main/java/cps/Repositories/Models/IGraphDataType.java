package cps.Repositories.Models;

public interface IGraphDataType {
    Double getValue();
    void setValue(Double value);
    Long getId();
    WeatherData getWeatherData();
    void setWeatherData(WeatherData weatherData);
}
