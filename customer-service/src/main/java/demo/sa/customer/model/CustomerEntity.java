package demo.sa.customer.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long databaseId;

    @Column(nullable = false, updatable = false, unique = true)
    private String customerId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Salutation salutation;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AddressEntity> addresses = new HashSet<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PhoneEntity> phones = new HashSet<>();

    public enum Salutation {
        MR,
        MRS,
        COMPANY;
    }


}