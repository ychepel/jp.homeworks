package app.service;

import app.domain.Weather;

public interface WeatherService {

    Weather getByCity(String city);
    void setConvertedTemperature(Weather weather);
}
