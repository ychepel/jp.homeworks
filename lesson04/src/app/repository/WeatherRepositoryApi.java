package app.repository;

import app.domain.Weather;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Repository
@PropertySource("classpath:application.properties")
public class WeatherRepositoryApi implements WeatherRepository {

    private static final String WEATHERAPI_PATH = "https://api.weatherapi.com/v1/current.json";

    private final String apiUrl;

    public WeatherRepositoryApi(@Value("${weatherapi.key}") String apiKey) {
        this.apiUrl = WEATHERAPI_PATH + "?key=" + apiKey + "&aqi=no" + "&q=";
    }

    @Override
    public Weather getByCity(String city) {
        try {
            URL url = new URL(apiUrl + city);
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String data = reader.readLine();
            JSONObject jsonObject = new JSONObject(data);
            Weather weather = new Weather(
                    jsonObject.getJSONObject("location").getString("name"),
                    jsonObject.getJSONObject("current").getJSONObject("condition").getString("text"),
                    jsonObject.getJSONObject("current").getInt("temp_c")
            );
            return weather;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
