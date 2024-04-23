package app.controller;

import app.domain.Weather;
import app.service.WeatherService;
import org.springframework.stereotype.Component;

@Component
public class WeatherController {

    private WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    public Weather getByCity(String city) {
        return service.getByCity(city);
    }
}
