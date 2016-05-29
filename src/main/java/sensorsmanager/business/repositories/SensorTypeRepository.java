package sensorsmanager.business.repositories;

import org.springframework.data.repository.CrudRepository;

import sensorsmanager.business.entities.SensorType;

public interface SensorTypeRepository extends CrudRepository<SensorType, Long> {

    SensorType findSensorTypeByName(String name);

}
