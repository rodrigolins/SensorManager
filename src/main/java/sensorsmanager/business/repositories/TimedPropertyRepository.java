package sensorsmanager.business.repositories;

import org.springframework.data.repository.CrudRepository;
import sensorsmanager.business.entities.TimedProperty;

public interface TimedPropertyRepository extends CrudRepository<TimedProperty, Long> {

}
