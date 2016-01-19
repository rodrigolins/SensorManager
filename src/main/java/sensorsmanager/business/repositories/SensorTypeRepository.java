package sensorsmanager.business.repositories;

import org.springframework.data.repository.CrudRepository;
import sensorsmanager.business.entities.SensorType;

import java.util.List;

public interface SensorTypeRepository extends CrudRepository<SensorType, Long> {

    SensorType findSensorTypeByName(String name);

}
