package demo.sa.product.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long databaseId;

    @Column(nullable = false, updatable = false, unique = true)
    private String productId;

    @Column
    private String shortDescription;

    @Column
    private String longDescription;

    @Column
    private String name;

    @Column
    private Float price;

    @Column
    private Long stock;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AssetEntity> assetEntities = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PromotionEntity> promotionEntities = new HashSet<>();

}