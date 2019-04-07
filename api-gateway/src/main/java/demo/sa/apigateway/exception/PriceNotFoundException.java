package demo.sa.apigateway.exception;

public class PriceNotFoundException extends RuntimeException{
    public PriceNotFoundException(String s) {
        super(s);
    }
}
