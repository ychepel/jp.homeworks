package app.domain;

import java.util.Objects;

public class Weather {

    private String city;
    private String condition;
    private int celsius;
    private double fahrenheit;

    public Weather(String city, String condition, int celsius) {
        this.city = city;
        this.condition = condition;
        this.celsius = celsius;
        this.fahrenheit = (1.8 * this.celsius) + 32;
    }

    public int getCelsius() {
        return celsius;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return celsius == weather.celsius && fahrenheit == weather.fahrenheit && Objects.equals(city, weather.city) && Objects.equals(condition, weather.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, condition, celsius, fahrenheit);
    }

    @Override
    public String toString() {
        return String.format("Weather in %s: %d°C (%.1f° Fahrenheit), %s", city, celsius, fahrenheit, condition);
    }
}
