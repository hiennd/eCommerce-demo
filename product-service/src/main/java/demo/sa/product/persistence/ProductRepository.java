package demo.sa.product.persistence;

import demo.sa.product.model.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByProductId(String productId);
}
