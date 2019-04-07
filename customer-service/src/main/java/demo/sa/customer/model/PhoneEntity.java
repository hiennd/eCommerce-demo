package demo.sa.customer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString(exclude = {"customer"})
@EqualsAndHashCode(exclude = {"customer"})
@Entity
@Table(name = "phones")
public class PhoneEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long databaseId;

    @Column(nullable = false, updatable = false, unique = true)
    private String phoneId;

    @Column
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NumberType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerEntity customer;

    public enum NumberType {
        MOBIL,
        LANDLINE;
    }
}
