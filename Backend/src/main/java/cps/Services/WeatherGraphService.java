package cps.Services;

import cps.Controllers.DTO.GraphDataDTO;
import cps.Repositories.*;
import cps.Repositories.Models.IGraphDataType;
import cps.Repositories.Models.WeatherData;
import cps.Services.Util.DataTypes;
import cps.Services.Util.Util;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
class WeatherGraphService {
    private final WeatherDataRepository weatherDataRepository;
    private final Map<DataTypes, JpaRepository<?, Long>> dataTypeRepositories = new HashMap<>();

    WeatherGraphService(TemperatureRepository temperatureRepository,
                               HumidityRepository humidityRepository,
                               RainRepository rainRepository,
                               SolarRadiationRepository solarRadiationRepository,
                               SunMinRepository sunMinRepository,
                               WindSpeedRepository windSpeedRepository,
                               WindDirectionRepository windDirectionRepository,
                               CloudCoverageRepository cloudCoverageRepository,
                               WeatherDataRepository weatherDataRepository) {

        this.weatherDataRepository = weatherDataRepository;
        dataTypeRepositories.put(DataTypes.TEMPERATURE,temperatureRepository);
        dataTypeRepositories.put(DataTypes.HUMIDITY,humidityRepository);
        dataTypeRepositories.put(DataTypes.RAIN,rainRepository);
        dataTypeRepositories.put(DataTypes.SOLAR_RADIATION,solarRadiationRepository);
        dataTypeRepositories.put(DataTypes.SUN_MIN,sunMinRepository);
        dataTypeRepositories.put(DataTypes.WIND_SPEED,windSpeedRepository);
        dataTypeRepositories.put(DataTypes.WIND_DIRECTION,windDirectionRepository);
        dataTypeRepositories.put(DataTypes.CLOUD_COVERAGE,cloudCoverageRepository);

    }

    List<GraphDataDTO> getData(DataTypes dataType) {
        List<IGraphDataType> entities = (List<IGraphDataType>) dataTypeRepositories.get(dataType).findAll();
        List<WeatherData> weatherData = weatherDataRepository.findAll();
        Map<Long, String> timestampMap = new HashMap<>();
        for (WeatherData data : weatherData) {
            timestampMap.put(data.getId(), Util.getDateTime(data.getTimestamp()));
        }

        List<GraphDataDTO> graphDataDTOList = new ArrayList<>();
        for (IGraphDataType entity : entities) {
            if(entity.getWeatherData()==null){
                continue;
            }
            GraphDataDTO graphDataDTO = new GraphDataDTO();
            graphDataDTO.setValue(entity.getValue());
            graphDataDTO.setTimeStamp(timestampMap.get(entity.getWeatherData().getId()));
            graphDataDTOList.add(graphDataDTO);
        }
        return graphDataDTOList;
    }
}
