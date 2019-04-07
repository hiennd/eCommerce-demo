package demo.sa.product.persistence;

import demo.sa.product.model.AssetEntity;
import demo.sa.product.model.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetRepository extends CrudRepository<AssetEntity, Long> {
}
