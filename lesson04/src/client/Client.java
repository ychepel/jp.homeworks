package client;

import app.controller.WeatherController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Client {

    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext("app");
        WeatherController controller = context.getBean(WeatherController.class);
        System.out.println(controller.getByCity("California"));
    }
}
