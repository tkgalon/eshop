package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class CarRepository implements GenericRepository<Car> {

    static int id = 0;

    private List<Car> carData = new ArrayList<>();

    @Override
    public Car create(Car car){
        if (car.getCarId() == null){
            UUID uuid = UUID.randomUUID();
            car.setCarId(uuid.toString());
        }
        carData.add(car);
        return car;
    }

    @Override
    public Car update(Car updatedCar) {
        for (Car car : carData){
            if (car.getCarId().equals(updatedCar.getCarId())){
                car.setCarName(updatedCar.getCarName());
                car.setCarColor(updatedCar.getCarColor());
                car.setCarQuantity(updatedCar.getCarQuantity());
                return car;
            }
        }
        return null;
    }

    @Override
    public void delete(String id){ carData.removeIf(car -> car.getCarId().equals(id));}

    @Override
    public Iterator<Car> findAll(){
        return carData.iterator();
    }

    @Override
    public Car findById(String id){
        for (Car car : carData){
            if (car.getCarId().equals(id)){
                return car;
            }
        }
        return null;
    }
}