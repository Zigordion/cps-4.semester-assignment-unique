package cps.Services;

import cps.Repositories.Models.WeatherData;
import cps.Repositories.Models.WeatherStation;
import cps.Services.Util.ApiClient;
import cps.Services.Util.Util;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DmiClient implements IApiClient {
    protected URI uri;
    private final ArrayList<String> parameterIds = new ArrayList<>(List.of(new String[]{
            "precip_past10min",
            "temp_dry",
            "humidity",
            "wind_speed",
            "wind_dir",
            "cloud_cover",
            "sun_last10min_glob",
            "radia_glob"}));
    public DmiClient() {

        uri = UriComponentsBuilder.fromUriString("https://dmigw.govcloud.dk/v2/metObs/collections/observation/items")
                .queryParam("period", "latest-10-minutes")
                .queryParam("bbox", "9.938,55.2629,10.8478,55.6235")
                .queryParam("api-key", "03c12439-02f5-45e5-92c1-8e633abfa088")
                .build()
                .toUri();
    }
    @Override
    public WeatherData constructWeatherData(WeatherDataBuilder weatherDataBuilder, WeatherStation weatherStation) {
        HashMap<String, Double> valueMap = new HashMap<>();
        HttpResponse<String> response = ApiClient.query(uri);
        JSONArray featureArray = new JSONObject(response.body()).getJSONArray("features");
        for (int i = 0; i < featureArray.length(); i++) {
            String parameterId = featureArray.getJSONObject(i).getJSONObject("properties").getString("parameterId");
            if (!parameterIds.contains(parameterId)) {
                continue;
            }
            double val = featureArray.getJSONObject(i).getJSONObject("properties").getDouble("value");

            valueMap.put(parameterId, val);
        }
        String time = featureArray.getJSONObject(0).getJSONObject("properties").getString("observed");
        Timestamp timestamp = Util.getTimestampFromString(time);
        System.out.println(valueMap);
        return weatherDataBuilder
                .setRain(valueMap.get("precip_past10min"))
                .setTemperature(valueMap.get("temp_dry"))
                .setHumidity(valueMap.get("humidity"))
                .setSolarRad(valueMap.get("radia_glob"))
                .setCloudCoverage(valueMap.get("cloud_cover"))
                .setSunMin(valueMap.get("sun_last10min_glob"))
                .setWindDirection(valueMap.get("wind_dir"))
                .setWindSpeed(valueMap.get("wind_speed"))
                .setWeatherStation(weatherStation)
                .setTimestamp(timestamp)
                .build();
    }

}
