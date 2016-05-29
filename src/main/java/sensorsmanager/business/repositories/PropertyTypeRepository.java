package sensorsmanager.business.repositories;

import org.springframework.data.repository.CrudRepository;

import sensorsmanager.business.entities.PropertyType;

public interface PropertyTypeRepository extends CrudRepository<PropertyType, Long> {
	
	PropertyType findPropertyTypeByName(String name);

}
