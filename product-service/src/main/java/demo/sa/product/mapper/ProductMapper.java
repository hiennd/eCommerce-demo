package demo.sa.product.mapper;

import demo.sa.product.model.ProductEntity;
import demo.sa.product.model.dto.Product;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends ConfigurableMapper {

    protected void configure(final MapperFactory factory) {

        factory.classMap(ProductEntity.class, Product.class)
                .mapNulls(true)
                .byDefault()
                .register();
    }
}
