package demo.sa.price.exception;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(String productId, String promotionId) {
        super("Price Info Not Found. ProductId:" + productId + ". PromotionId:" + promotionId);
    }
}
