package demo.sa.price.mapper;

import demo.sa.price.model.PriceEntity;
import demo.sa.price.model.dto.Price;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper extends ConfigurableMapper {

    protected void configure(final MapperFactory factory) {

        factory.classMap(PriceEntity.class, Price.class)
                .mapNulls(true)
                .byDefault()
                .register();
    }
}
