package demo.sa.order.model;

import lombok.Data;

@Data
public class Address {

    private String firstName;
    private String lastName;
    private String street;
    private String postalCode;
    private String countryCode;
}
