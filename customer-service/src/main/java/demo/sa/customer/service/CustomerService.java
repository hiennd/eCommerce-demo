package demo.sa.customer.service;

import demo.sa.customer.exception.CustomerNotFoundException;
import demo.sa.customer.mapper.CustomerMapper;
import demo.sa.customer.model.CustomerEntity;
import demo.sa.customer.model.dto.Customer;
import demo.sa.customer.persistence.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public Customer createCustomer(Customer customer) {
        CustomerEntity customerEntity = customerMapper.map(customer, CustomerEntity.class);
        CustomerEntity newCustomer = customerRepository.save(customerEntity);
        return customerMapper.map(newCustomer, Customer.class);
    }

    public Customer getCustomer(String customerId) {
        CustomerEntity customerEntity = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        return customerMapper.map(customerEntity, Customer.class);
    }

    public List<Customer> getCustomers() {
        List<CustomerEntity> customerEntities = (List<CustomerEntity>) customerRepository.findAll();
        return customerEntities
                .stream()
                .map(e -> customerMapper.map(e, Customer.class))
                .collect(Collectors.toList());
    }

    public void deleteCustomer(String customerId) {
        CustomerEntity customerEntity = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        customerRepository.delete(customerEntity);

    }

    public Customer updateCustomer(String customerId, Customer customer) {
        CustomerEntity customerEntity = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        CustomerEntity newCustomerEntity = customerMapper.map(customer, CustomerEntity.class);
        newCustomerEntity.setDatabaseId(customerEntity.getDatabaseId());
        customerRepository.save(newCustomerEntity);
        return customer;
    }
}
