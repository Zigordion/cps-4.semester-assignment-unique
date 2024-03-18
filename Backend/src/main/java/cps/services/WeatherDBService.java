package cps.services;

import cps.models.WeatherData;
import cps.repositories.WeatherDataRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WeatherDBService {
    WeatherDataRepository weatherDataRepository;

    public WeatherDBService(WeatherDataRepository weatherDataRepository) {
        //Dependency injection. This repository is created automatically by spring
        this.weatherDataRepository = weatherDataRepository;
    }

    public void addWeatherToDB(WeatherData weatherData) {
        //TMP method
        weatherDataRepository.save(weatherData);
    }

    public WeatherData getWeatherData(Long id) {
        return weatherDataRepository.findById(id).orElse(null);
    }

    // API key to dmi: 4ed024e9-49f9-4286-b988-5c97597774db
//4046b36a-f328-471f-91db-0249dbf703d3
//        URI uri = UriComponentsBuilder.fromUriString("https://dmigw.govcloud.dk/v1/forecastdata")
//                .queryParam("api-key", "4ed024e9-49f9-4286-b988-5c97597774db")
//                .build()
//                .toUri();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(uri)
//                .GET()
//                .build();
    //https://dmigw.govcloud.dk/v2/metObs/collections/observation/items?period=latest-10-minutes&parameterId=temp_dry&bbox=9.938,55.2629,10.8478,55.6235&api-key=03c12439-02f5-45e5-92c1-8e633abfa088
    ArrayList<String> parameterIds = new ArrayList<>(List.of(new String[]{"precip_dur_past10min", "temp_dry", "humidity", "wind_min", "wind_dir", "cloud_cover", "sun_last10min_glob", "radia_glob"}));

    public WeatherData getAllValues() {
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
        HashMap<String, Double> valueMap = new HashMap<>();
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        JSONArray featureArray = new JSONObject(response.body()).getJSONArray("features");
        for (int i = 0; i < featureArray.length(); i++) {
            String parameterId = featureArray.getJSONObject(i).getJSONObject("properties").getString("parameterId");
            if (!parameterIds.contains(parameterId)) {
                continue;
            }
            valueMap.put(parameterId, featureArray.getJSONObject(i).getJSONObject("properties").getDouble("value"));
        }
        String time = featureArray.getJSONObject(0).getJSONObject("properties").getString("observed");
        Timestamp timestamp = getTimestampFromString(time);
        return new WeatherData(
                valueMap.get("temp_dry"),
                valueMap.get("wind_min"),
                valueMap.get("wind_dir"),
                valueMap.get("sun_last10min_glob"),
                valueMap.get("cloud_cover"),
                valueMap.get("humidity"),
                valueMap.get("precip_dur_past10min"),
                valueMap.get("radia_glob"),
                timestamp
        );
    }

    private Timestamp getTimestampFromString(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date = sdf.parse(time);
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDateTime() {
        Timestamp ts = getAllValues().getTimestamp();
        Date date = new Date(ts.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        return sdf.format(date);

    }

    public double getOverallWeather() {

        WeatherData wd = getAllValues();

        if (wd.getRain() > 1) {
            return 1;
        } else if (wd.getWindSpeed() > 8) {
            return 2;
        } else if (wd.getCloudCoverage() > 60) {
            return 3;
        } else {
            return 4;
        }

    }

}
