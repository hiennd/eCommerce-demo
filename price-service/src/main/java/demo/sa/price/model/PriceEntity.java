package demo.sa.price.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "prices")
public class PriceEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceId;

    @Column
    private String productId;

    @Column
    private String promotionId;
}