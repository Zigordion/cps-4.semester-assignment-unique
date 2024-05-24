package cps.BusinessLogic;

import cps.Presentation.DTO.WeatherDataDTO;
import cps.DataAccess.Models.WeatherData;
import cps.DataAccess.Models.WeatherStation;
import cps.Util.Data.SSETopic;
import cps.Util.TimeStampConverter;
import cps.Util.Data.WeatherStations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
class WeatherService {
    private final DataBaseService dataBaseService;
    private final WeatherDataBuilder weatherDataBuilder;
    private final SseService sseService;

    private final IApiClient IApiClient;

    WeatherService(DataBaseService dataBaseService, WeatherDataBuilder weatherDataBuilder, SseService sseService) {
        this.dataBaseService = dataBaseService;
        this.weatherDataBuilder = weatherDataBuilder;
        this.sseService = sseService;
        IApiClient = new DmiClient();
    }

    @Scheduled(fixedRate = 600_000) //equal  1000 = 1 sec * 60*10 10 min.
    void fetchAndStoreValuesInDB() {
        WeatherData latestWeatherData = dataBaseService.getLatestValueFromDB();
        WeatherStation weatherStation = dataBaseService.getWeatherStationFromName(WeatherStations.DMI.name());
        WeatherData weatherData = IApiClient.constructWeatherData(weatherDataBuilder, weatherStation);
        Timestamp timestamp = weatherData.getTimestamp();
        if (latestWeatherData != null && timestamp.equals(latestWeatherData.getTimestamp())) {
            System.out.println("Already exists in db; skipping");
            return;
        }
        dataBaseService.save(weatherData);
        sseService.sendToClients(SSETopic.WEATHER_DATA,new WeatherDataDTO(weatherData));
    }



    String getDateTime() {
        Timestamp ts = dataBaseService.getLatestValueFromDB().getTimestamp();
        return TimeStampConverter.getDateTime(ts);
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
