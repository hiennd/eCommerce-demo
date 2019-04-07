package demo.sa.price.persistence;

import demo.sa.price.model.PriceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends CrudRepository<PriceEntity, Long> {

    Optional<PriceEntity> findByProductIdAndPromotionId(String productId, String promotionId);
}
