package app.service;

import app.domain.Weather;
import app.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    private WeatherRepository repository;

    public WeatherServiceImpl(@Qualifier("weatherRepositoryApi") WeatherRepository repository) {
        this.repository = repository;
    }

    @Override
    public Weather getByCity(String city) {
        Weather weather = repository.getByCity(city);
        setConvertedTemperature(weather);
        return weather;
    }

    @Override
    public void setConvertedTemperature(Weather weather) {
        weather.setFahrenheit((1.8 * weather.getCelsius()) + 32);
    }
}
