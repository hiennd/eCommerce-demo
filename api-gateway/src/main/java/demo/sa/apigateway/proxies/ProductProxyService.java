package demo.sa.apigateway.proxies;

import demo.sa.apigateway.exception.ProductNotFoundException;
import demo.sa.product.model.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductProxyService {

    private WebClient client;
    private String productServiceUri;

    @Autowired
    public ProductProxyService(@Qualifier("productWebClient") WebClient client,
                               @Value("${product.services_url:localhost:8082}") String productServiceUri) {
        this.client = client;
        this.productServiceUri = productServiceUri;
    }

    public Mono<Product> findProductById(String productId) {
        Mono<ClientResponse> response = client
                .get()
                .uri(productServiceUri + "/products/{productId}", productId)
                .exchange();
        return response.flatMap(resp -> {
            switch (resp.statusCode()) {
                case OK:
                    return resp.bodyToMono(Product.class);
                case NOT_FOUND:
                    return Mono.error(new ProductNotFoundException());
                default:
                    return Mono.error(new RuntimeException("Unknown" + resp.statusCode()));
            }
        });
    }

    public Mono<Product> postNewProduct(Product product) {
        Mono<Product> productMono = client
                .post()
                .uri(productServiceUri + "/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(product))
                .retrieve()
                .bodyToMono(Product.class);
        return productMono.flatMap(Mono::just);
    }

    public Flux<Product> getProducts() {
        return client.get()
                .uri(productServiceUri + "/products")
                .retrieve()
                .bodyToFlux(Product.class);
    }

    public Mono<Product> updateProduct(Product product, String productId) {
        Mono<Product> productMono = client
                .put()
                .uri(productServiceUri + "/products/" + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(product))
                .retrieve()
                .bodyToMono(Product.class);
        return productMono.flatMap(Mono::just);
    }
}
