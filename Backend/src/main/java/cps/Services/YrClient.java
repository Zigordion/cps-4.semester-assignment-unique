package cps.Services;

import cps.Repositories.Models.WeatherData;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class YrClient extends ApiClient {
    public YrClient() {
        uri = UriComponentsBuilder.fromUriString("https://api.met.no/weatherapi/locationforecast/2.0/compact")
                .queryParam("lat",51.5)
                .queryParam("lon",0)
                .build()
                .toUri();
    }

    @Override
    public WeatherData constructWeatherData() {
        return null;
    }
}
