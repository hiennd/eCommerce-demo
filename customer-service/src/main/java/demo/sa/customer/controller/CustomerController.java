package demo.sa.customer.controller;

import demo.sa.customer.CustomerApi;
import demo.sa.customer.model.dto.Customer;
import demo.sa.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<Customer> createCustomer(Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(String customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Customer> getCustomer(String customerId) {
        return ResponseEntity.ok(customerService.getCustomer(customerId));
    }

    @Override
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @Override
    public ResponseEntity<Customer> updateCustomer(String customerId, Customer customer) {
        return ResponseEntity.ok(customerService.updateCustomer(customerId, customer));
    }
}
