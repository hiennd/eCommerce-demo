package demo.sa.product.mapper;

import demo.sa.product.model.AssetEntity;
import demo.sa.product.model.ProductEntity;
import demo.sa.product.model.PromotionEntity;
import demo.sa.product.model.dto.Asset;
import demo.sa.product.model.dto.Product;
import demo.sa.product.model.dto.Promotion;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends ConfigurableMapper {

    protected void configure(final MapperFactory factory) {

        factory.classMap(ProductEntity.class, Product.class)
                .mapNulls(true)
                .field("promotionEntities", "promotions")
                .field("assetEntities", "assets")
                .byDefault()
                .register();
        factory.classMap(AssetEntity.class, Asset.class)
                .mapNulls(true)
                .byDefault()
                .register();
        factory.classMap(PromotionEntity.class, Promotion.class)
                .mapNulls(true)
                .byDefault()
                .register();
    }
}
