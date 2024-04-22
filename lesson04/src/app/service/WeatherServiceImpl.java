package app.service;

import app.domain.Weather;
import app.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class WeatherServiceImpl implements WeatherService {

    private WeatherRepository repository;
    private WeatherCacheService cacheService;

    public WeatherServiceImpl(
            @Qualifier("weatherRepositoryApi") WeatherRepository repository,
            WeatherCacheService cacheService
    ) {
        this.repository = repository;
        this.cacheService = cacheService;
    }

    @Override
    public Weather getByCity(String city) {
        Weather weather = getFromCache(city);
        if (Objects.isNull(weather)) {
            weather = repository.getByCity(city);
            putToCache(weather);
        }
        return weather;
    }

    private Weather getFromCache(String city) {
        return cacheService.get(city);
    }

    private void putToCache(Weather weather) {
        cacheService.put(weather);
    }
}
