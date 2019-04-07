package demo.sa.product.model;

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
@ToString(exclude = {"product"})
@EqualsAndHashCode(exclude = {"product"})
@Entity
@Table(name = "promotions")
public class PromotionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String promotionId;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;

}
