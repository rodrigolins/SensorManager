package sensorsmanager.business.repositories;

import org.springframework.data.repository.CrudRepository;
import sensorsmanager.business.entities.Rule;

public interface RuleRepository extends CrudRepository<Rule, Long> {

}
