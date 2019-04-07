package demo.sa.price.controller;

import demo.sa.price.PriceApi;
import demo.sa.price.model.dto.Price;
import demo.sa.price.service.PriceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PriceController implements PriceApi {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @Override
    public ResponseEntity<Price> createPrice(Price price) {
        return ResponseEntity.status(HttpStatus.CREATED).body(priceService.createPrice(price));
    }

    @Override
    public ResponseEntity<Price> getPrice(String productId, String promotionId) {
        return ResponseEntity.ok(priceService.getPrice(productId, promotionId));
    }

    @Override
    public ResponseEntity<Price> updatePrice(String productId, String promotionId, Price price) {
        return ResponseEntity.ok(priceService.updatePrice(productId, promotionId, price));
    }

}
