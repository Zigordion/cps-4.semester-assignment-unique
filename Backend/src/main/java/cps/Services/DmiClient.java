package cps.Services;

import cps.Repositories.Models.WeatherData;
import cps.Repositories.Models.WeatherStation;
import cps.Services.Util.Util;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DmiClient extends ApiClient {
    private final ArrayList<String> parameterIds = new ArrayList<>(List.of(new String[]{
            "precip_past10min",
            "temp_dry",
            "humidity",
            "wind_speed",
            "wind_dir",
            "cloud_cover",
            "sun_last10min_glob",
            "radia_glob"}));
    private JSONArray featureArray;
    public DmiClient() {

        uri = UriComponentsBuilder.fromUriString("https://dmigw.govcloud.dk/v2/metObs/collections/observation/items")
                .queryParam("period", "latest-10-minutes")
                .queryParam("bbox", "9.938,55.2629,10.8478,55.6235")
                .queryParam("api-key", "03c12439-02f5-45e5-92c1-8e633abfa088")
                .build()
                .toUri();
    }
    @Override
    public Timestamp getTimestamp(){
        HttpResponse<String> response = query();
        featureArray = new JSONObject(response.body()).getJSONArray("features");
        String time = featureArray.getJSONObject(0).getJSONObject("properties").getString("observed");
        return Util.getTimestampFromString(time);
    }
    @Override
    public WeatherData constructWeatherData(WeatherStation weatherStation, Timestamp timestamp) {
        WeatherData weatherData = new WeatherData();
        weatherData.setWeatherStation(weatherStation);
        weatherData.setTimestamp(timestamp);
        return weatherData;

    }
    public void fillData(WeatherData weatherData, DataBuilder dataBuilder){
        HashMap<String, Double> valueMap = new HashMap<>();
        for (int i = 0; i < featureArray.length(); i++) {
            String parameterId = featureArray.getJSONObject(i).getJSONObject("properties").getString("parameterId");
            if (!parameterIds.contains(parameterId)) {
                continue;
            }
            double val = featureArray.getJSONObject(i).getJSONObject("properties").getDouble("value");

            valueMap.put(parameterId, val);
        }
        System.out.println(valueMap);

    }

}
