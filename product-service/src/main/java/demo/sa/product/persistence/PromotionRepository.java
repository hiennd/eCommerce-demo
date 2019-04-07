package demo.sa.product.persistence;

import demo.sa.product.model.ProductEntity;
import demo.sa.product.model.PromotionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends CrudRepository<PromotionEntity, Long> {
}
