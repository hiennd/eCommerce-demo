package demo.sa.order.mapper;

import demo.sa.order.model.Address;
import demo.sa.order.model.OrderEntity;
import demo.sa.order.model.OrderItem;
import demo.sa.order.model.Product;
import demo.sa.order.model.dto.AddressDto;
import demo.sa.order.model.dto.OrderDto;
import demo.sa.order.model.dto.OrderItemDto;
import demo.sa.order.model.dto.ProductDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper extends ConfigurableMapper {

    protected void configure(final MapperFactory factory) {

        factory.classMap(OrderEntity.class, OrderDto.class)
                .mapNulls(true)
                .byDefault()
                .register();

        factory.classMap(Address.class, AddressDto.class)
                .mapNulls(true)
                .byDefault()
                .register();
        factory.classMap(OrderItem.class, OrderItemDto.class)
                .mapNulls(true)
                .byDefault()
                .register();
        factory.classMap(Product.class, ProductDto.class)
                .mapNulls(true)
                .byDefault()
                .register();
    }
}
