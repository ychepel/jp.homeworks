package app.service;

import app.domain.Weather;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherCacheServiceImpl implements WeatherCacheService {

    private Map<String, Weather> cache = new HashMap<>();

    @Override
    public void put(Weather weather) {
        cache.put(weather.getCity(), weather);
    }

    @Override
    public Weather get(String city) {
        return cache.get(city);
    }
}
