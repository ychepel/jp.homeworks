package app.service;

import app.domain.Weather;

public interface WeatherCacheService {

    void put(Weather weather);
    Weather get(String city);
}
