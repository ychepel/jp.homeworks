package app.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CarRepositoryHibernate implements CarRepository {

    private final EntityManager entityManager;

    public CarRepositoryHibernate() {
        this.entityManager = new Configuration()
                .configure("mysql.cfg.xml")
                .buildSessionFactory()
                .createEntityManager();
    }

    @Override
    public Car save(Car car) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(car);
        transaction.commit();
        return car;
    }

    @Override
    public Car getById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Car car = entityManager.find(Car.class, id);
        transaction.commit();
        return car;
    }

    @Override
    public List<Car> getAll() {
        Query query = entityManager.createQuery("SELECT c FROM Car c");
        return query.getResultList();
    }

    @Override
    public void update(Car car) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Car dbCar = entityManager.find(Car.class, car.getId());
        dbCar.setPrice(car.getPrice());
        entityManager.persist(dbCar);
        transaction.commit();
    }

    @Override
    public void deleteById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Car car = entityManager.find(Car.class, id);
        entityManager.remove(car);
        transaction.commit();
    }
}
