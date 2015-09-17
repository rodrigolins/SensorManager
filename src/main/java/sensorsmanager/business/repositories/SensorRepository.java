package sensorsmanager.business.repositories;

import org.springframework.data.repository.CrudRepository;

import sensorsmanager.business.entities.Sensor;

public interface SensorRepository extends CrudRepository<Sensor, Long> {

}
