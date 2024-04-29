package cps.Services;

import cps.Repositories.Models.WeatherData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static cps.Services.Util.getTimestampFromString;

@Component("weatherStation")
public class DmiWeatherStation implements IWeatherStation{
    private final ArrayList<String> parameterIds = new ArrayList<>(List.of(new String[]{"precip_past10min", "temp_dry", "humidity", "wind_speed", "wind_dir", "cloud_cover", "sun_last10min_glob", "radia_glob"}));

    @Override
    public WeatherData getWeatherData() {
        HashMap<String, Double> valueMap = new HashMap<>();
        HttpResponse<String> response = query();
        JSONArray featureArray = new JSONObject(response.body()).getJSONArray("features");
        for (int i = 0; i < featureArray.length(); i++) {
            String parameterId = featureArray.getJSONObject(i).getJSONObject("properties").getString("parameterId");
            if (!parameterIds.contains(parameterId)) {
                continue;
            }
            double val = featureArray.getJSONObject(i).getJSONObject("properties").getDouble("value");
            System.out.println(parameterId + " : " + val);

            valueMap.put(parameterId, val);
        }
        Timestamp timestamp = getTime();
        WeatherData weatherData = new WeatherData();
        weatherData.setRain(valueMap.get("precip_past10min")!=null ? valueMap.get("precip_past10min") : 0);
        weatherData.setTimestamp(timestamp);
        weatherData.setTemperature(valueMap.get("temp_dry")!=null ? valueMap.get("temp_dry") : 0);
        weatherData.setHumidity(valueMap.get("humidity")!=null ? valueMap.get("humidity") : 0);
        weatherData.setSolarRad(valueMap.get("radia_glob")!= null ? valueMap.get("radia_glob") : 0);
        weatherData.setCloudCoverage(valueMap.get("cloud_cover")!=null ? valueMap.get("cloud_cover") : 0);
        weatherData.setSunMin(valueMap.get("sun_last10min_glob")!=null ? valueMap.get("sun_last10min_glob") : 0);
        weatherData.setWindDirection(valueMap.get("wind_dir")!=null ? valueMap.get("wind_dir") : 0);
        weatherData.setWindSpeed(valueMap.get("wind_speed")!=null ? valueMap.get("wind_speed") : 0);
        return weatherData;
    }
    @Override
    public Timestamp getTime(){
        HttpResponse<String> response = query();
        JSONArray featureArray = new JSONObject(response.body()).getJSONArray("features");
        String time = featureArray.getJSONObject(0).getJSONObject("properties").getString("observed");
        return getTimestampFromString(time);
    }

    private HttpResponse<String> query(){
        URI uri = UriComponentsBuilder.fromUriString("https://dmigw.govcloud.dk/v2/metObs/collections/observation/items")
                .queryParam("period", "latest-10-minutes")
                .queryParam("bbox", "9.938,55.2629,10.8478,55.6235")
                .queryParam("api-key", "03c12439-02f5-45e5-92c1-8e633abfa088")
                .build()
                .toUri();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public WeatherStationType getWeatherStationType() {
        return WeatherStationType.DMI;
    }
}
