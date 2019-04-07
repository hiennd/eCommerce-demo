package demo.sa.product.controller;

import demo.sa.product.ProductApi;
import demo.sa.product.model.dto.Product;
import demo.sa.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductController implements ProductApi {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<Product> createProduct(Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(String productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Product> getProduct(String productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @Override
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @Override
    public ResponseEntity<Product> updateProduct(String productId, Product product) {
        return ResponseEntity.ok(productService.updateProduct(productId, product));
    }
}
