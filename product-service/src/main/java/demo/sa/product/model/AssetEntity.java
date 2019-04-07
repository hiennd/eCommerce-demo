package demo.sa.product.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString(exclude = {"product"})
@EqualsAndHashCode(exclude = {"product"})
@Entity
@Table(name = "assets")
public class AssetEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String assetId;

    @Column
    private String alt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AssetType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;

    public enum AssetType {
        IMAGE,
        VIDEO;
    }
}
