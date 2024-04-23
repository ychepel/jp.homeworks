package app.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

public class CarServiceImpl implements CarService {

    private CarRepository repository;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String sortBy;

    public CarServiceImpl(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public CarService setMinPrice(BigDecimal price) {
        this.minPrice = price;
        return this;
    }

    @Override
    public CarService setMaxPrice(BigDecimal price) {
        this.maxPrice = price;
        return this;
    }

    @Override
    public CarService setSortedBy(String key) {
        this.sortBy = key;
        return this;
    }

    @Override
    public List<Car> getAll() {
        Stream<Car> stream = repository.getAll().stream();
        if (minPrice != null) {
            stream = stream.filter(car -> car.getPrice().compareTo(minPrice) >= 0);
        }
        if (maxPrice != null) {
            stream = stream.filter(car -> car.getPrice().compareTo(maxPrice) <= 0);
        }
        if (sortBy != null) {
            stream = stream.sorted((car1, car2) -> {
                if ("year".equals(sortBy)) {
                    return Integer.compare(car1.getYear(), car2.getYear());
                }
                if ("brand".equals(sortBy)) {
                    return car1.getBrand().compareTo(car2.getBrand());
                }
                if ("price".equals(sortBy)) {
                    return car1.getPrice().compareTo(car2.getPrice());
                }
                return Long.compare(car1.getId(), car2.getId());
            });
        }
        return stream.toList();
    }
}
