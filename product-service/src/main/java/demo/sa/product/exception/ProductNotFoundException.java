package demo.sa.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String customerId) {
        super("Product Not Found: " +customerId);
    }
}
