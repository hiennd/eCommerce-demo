package demo.sa.product.service;

import demo.sa.product.exception.ProductNotFoundException;
import demo.sa.product.mapper.ProductMapper;
import demo.sa.product.model.ProductEntity;
import demo.sa.product.model.dto.Product;
import demo.sa.product.persistence.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Product createProduct(Product product) {
        ProductEntity productEntity = productMapper.map(product, ProductEntity.class);
        ProductEntity newProduct = productRepository.save(productEntity);
        return productMapper.map(newProduct, Product.class);
    }

    public Product getProduct(String productId) {
        ProductEntity productEntity = productRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return productMapper.map(productEntity, Product.class);
    }

    public List<Product> getProducts() {
        List<ProductEntity> productEntities = (List<ProductEntity>) productRepository.findAll();
        return productEntities
                .stream()
                .map(e -> productMapper.map(e, Product.class))
                .collect(Collectors.toList());
    }

    public void deleteProduct(String productId) {
        ProductEntity productEntity = productRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        productRepository.delete(productEntity);

    }

    public Product updateProduct(String productId, Product product) {
        ProductEntity productEntity = productRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        ProductEntity newProductEntity = productMapper.map(product, ProductEntity.class);
        newProductEntity.setDatabaseId(productEntity.getDatabaseId());
        productRepository.save(newProductEntity);
        return product;
    }
}
