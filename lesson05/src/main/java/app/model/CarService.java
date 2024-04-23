package app.model;

import java.math.BigDecimal;
import java.util.List;

public interface CarService {

    CarService setMinPrice(BigDecimal price);
    CarService setMaxPrice(BigDecimal price);
    CarService setSortedBy(String key);
    List<Car> getAll();
}
