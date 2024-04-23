package app.repository;

import app.domain.Weather;

public interface WeatherRepository {

    Weather getByCity(String city);
}
