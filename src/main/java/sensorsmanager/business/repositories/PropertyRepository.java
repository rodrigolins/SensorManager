package sensorsmanager.business.repositories;

import org.springframework.data.repository.CrudRepository;

import sensorsmanager.business.entities.Property;

public interface PropertyRepository extends CrudRepository<Property, Long> {
	

}
