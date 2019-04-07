package demo.sa.customer.persistence;

import demo.sa.customer.model.PhoneEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends CrudRepository<PhoneEntity, Long> {
}
