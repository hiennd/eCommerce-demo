package demo.sa.customer.mapper;

import demo.sa.customer.model.AddressEntity;
import demo.sa.customer.model.CustomerEntity;
import demo.sa.customer.model.PhoneEntity;
import demo.sa.customer.model.dto.Address;
import demo.sa.customer.model.dto.Customer;
import demo.sa.customer.model.dto.Phone;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper extends ConfigurableMapper {

    protected void configure(final MapperFactory factory) {

        factory.classMap(CustomerEntity.class, Customer.class)
                .mapNulls(true)
                .byDefault()
                .register();

        factory.classMap(PhoneEntity.class, Phone.class)
                .mapNulls(true)
                .field("customer.customerId", "ownerId")
                .byDefault()
                .register();

        factory.classMap(AddressEntity.class, Address.class)
                .mapNulls(true)
                .field("customer.customerId", "ownerId")
                .byDefault()
                .register();
    }
}
