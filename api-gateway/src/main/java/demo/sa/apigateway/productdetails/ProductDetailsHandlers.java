package demo.sa.apigateway.productdetails;

import demo.sa.apigateway.exception.PriceNotFoundException;
import demo.sa.apigateway.exception.ProductNotFoundException;
import demo.sa.apigateway.proxies.PriceProxyService;
import demo.sa.apigateway.proxies.ProductProxyService;
import demo.sa.order.model.dto.OrderDto;
import demo.sa.product.model.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class ProductDetailsHandlers {

    private final ProductProxyService productService;

    private final PriceProxyService priceService;

    @Autowired
    public ProductDetailsHandlers(ProductProxyService productService, PriceProxyService priceService) {
        this.productService = productService;
        this.priceService = priceService;
    }

    public Mono<ServerResponse> getProductDetails(ServerRequest serverRequest) {

        String productId = serverRequest.pathVariable("productId");

        Mono<Product> product = productService.findProductById(productId);
        return product.flatMap(prd -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(fromObject(prd))
                    .onErrorResume(ProductNotFoundException.class, e -> ServerResponse.notFound().build())
        );
    }

    public Mono<ServerResponse> postNewProduct(ServerRequest serverRequest) {
        Mono<Product> product = serverRequest.bodyToMono(Product.class);
        return product
                .flatMap(productService::postNewProduct)
                .flatMap(od -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(od)))
                .onErrorResume(HttpStatusCodeException.class, e -> ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> getProducts(ServerRequest serverRequest) {
        Flux<Product> products = productService.getProducts();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(products, Product.class));
    }

    public Mono<ServerResponse> updateProduct(ServerRequest serverRequest) {
        Mono<Product> product = serverRequest.bodyToMono(Product.class);
        return product
                .flatMap(productService::updateProduct)
                .flatMap(od -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(od)))
                .onErrorResume(HttpStatusCodeException.class, e -> ServerResponse.badRequest().build());
    }
}
