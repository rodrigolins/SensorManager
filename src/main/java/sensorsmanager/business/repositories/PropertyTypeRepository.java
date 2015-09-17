package sensorsmanager.business.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import sensorsmanager.business.entities.PropertyType;

public interface PropertyTypeRepository extends CrudRepository<PropertyType, Long> {
	
	List<PropertyType> findPropertyTypeByName(String name);

}
