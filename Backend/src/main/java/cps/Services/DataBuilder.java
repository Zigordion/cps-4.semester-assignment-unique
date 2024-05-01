package cps.Services;

import cps.Repositories.*;
import cps.Repositories.Models.*;
import cps.Services.Util.DataTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class DataBuilder {
    private WeatherData weatherData;
    private final TemperatureRepository temperatureRepository;
    private final HumidityRepository humidityRepository;
    private final RainRepository rainRepository;
    private final SolarRadiationRepository solarRadiationRepository;
    private final SunMinRepository sunMinRepository;
    private final WindSpeedRepository windSpeedRepository;
    private final WindDirectionRepository windDirectionRepository;
    private final CloudCoverageRepository cloudCoverageRepository;

    public DataBuilder(TemperatureRepository temperatureRepository,
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

        dataTypeEntities.put(DataTypes.TEMPERATURE, Temperature::new);
        dataTypeEntities.put(DataTypes.HUMIDITY, Humidity::new);
        dataTypeEntities.put(DataTypes.RAIN, Rain::new);
        dataTypeEntities.put(DataTypes.SOLAR_RADIATION, SolarRadiation::new);
        dataTypeEntities.put(DataTypes.SUN_MIN, SunPrTen::new);
        dataTypeEntities.put(DataTypes.WIND_SPEED, WindSpeed::new);
        dataTypeEntities.put(DataTypes.WIND_DIRECTION, WindDirection::new);
        dataTypeEntities.put(DataTypes.CLOUD_COVERAGE, CloudCoverage::new);

    }

    Map<DataTypes, Supplier<IGraphDataType>> dataTypeEntities = new HashMap<>();

    public <T extends IGraphDataType> void setData(Double value, DataTypes dataType) {
        Map<DataTypes, JpaRepository<T, Long>> dataTypeRepositories = new HashMap<>();
        dataTypeRepositories.put(DataTypes.TEMPERATURE, temperatureRepository);
        dataTypeRepositories.put(DataTypes.HUMIDITY, humidityRepository);
        dataTypeRepositories.put(DataTypes.RAIN, rainRepository);
        dataTypeRepositories.put(DataTypes.SOLAR_RADIATION, solarRadiationRepository);
        dataTypeRepositories.put(DataTypes.SUN_MIN, sunMinRepository);
        dataTypeRepositories.put(DataTypes.WIND_SPEED, (JpaRepository<T, Long>) windSpeedRepository);
        dataTypeRepositories.put(DataTypes.WIND_DIRECTION, windDirectionRepository);
        dataTypeRepositories.put(DataTypes.CLOUD_COVERAGE, cloudCoverageRepository);


        Supplier<T> supplier = (Supplier<T>) dataTypeEntities.get(dataType);
        JpaRepository<T, Long> dataTypeRepository = dataTypeRepositories.get(dataType);
        if (supplier != null && dataTypeRepository != null) {

        }
        T datatypeEntity = supplier.get();
        .save(datatypeEntity);

    }

    public DataBuilder setTemperature(Double temperature) {
        if (temperature == null) {
            return this;
        }
        Temperature temp = new Temperature();
        temp.setValue(temperature);
        temp.setWeatherData(weatherData);

        temperatureRepository.save(temp);
        weatherData.setTemperature(temp);
        return this;
    }


}
