package sensorsmanager.business.repositories;

import org.springframework.data.repository.CrudRepository;

import sensorsmanager.business.entities.Sensor;
import sensorsmanager.business.entities.SensorType;

import java.util.List;

public interface SensorRepository extends CrudRepository<Sensor, Long> {

    Sensor findSensorByModelAndManufacturer(String model, String manufacturer);

    Sensor findSensorByModelAndManufacturerAndSensorType(String model, String manufacturer, SensorType sensorType);
}
