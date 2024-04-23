package app.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarRepositoryMap implements CarRepository {

    private Map<Long, Car> database = new HashMap<>();
    private long currentId;

    public CarRepositoryMap() {
        save(new Car("Renault", new BigDecimal(8000), 2011));
        save(new Car("Ford", new BigDecimal(13000), 2017));
        save(new Car("Audi", new BigDecimal(17500), 2020));
    }

    @Override
    public Car save(Car car) {
        car.setId(++currentId);
        database.put(currentId, car);
        return car;
    }

    @Override
    public Car getById(Long id) {
        return database.get(id);
    }

    @Override
    public List<Car> getAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public void update(Car car) {
        Car storedCar = database.get(car.getId());

        if (storedCar != null) {
            storedCar.setPrice(car.getPrice());
        }
    }

    @Override
    public void deleteById(Long id) {
        database.remove(id);
    }
}
