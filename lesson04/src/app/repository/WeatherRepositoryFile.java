package app.repository;

import app.domain.Weather;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;

@Primary
@Repository
public class WeatherRepositoryFile implements WeatherRepository {

    private static final String FILE_NAME = "database.txt";
    private static final String SEPARATOR = ";";

    @Override
    public Weather getByCity(String city) {
        String lowercaseCity = city.toLowerCase();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            return reader.lines()
                    .filter(row -> row.split(SEPARATOR)[0].toLowerCase().equals(lowercaseCity))
                    .limit(1)
                    .map(row -> {
                        String[] values = row.split(SEPARATOR);
                        int temperature = Integer.parseInt(values[1]);
                        return new Weather(values[0], values[2], temperature);
                    })
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
