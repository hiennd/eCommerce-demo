package demo.sa.customer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@ToString(exclude = {"customer"})
@EqualsAndHashCode(exclude = {"customer"})
@Entity
@Table(name = "addresses")
public class AddressEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long databaseId;

    @Column(nullable = false, updatable = false, unique = true)
    private String addressId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String street;

    @Column
    private String postalCode;

    @Column
    private String countryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerEntity customer;

}
