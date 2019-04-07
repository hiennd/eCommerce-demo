package demo.sa.apigateway.productdetails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ProductDetailsRouter {

    @Value("${products.service_url:localhost:8084}")
    private String serviceUri;


    @Bean
    public RouteLocator productProxyRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/products/**")
                        .and()
                        .method("PUT")
                        .uri(serviceUri))
                .route(r -> r.path("/products/**")
                        .and()
                        .method("POST")
                        .uri(serviceUri))
                .route(r -> r.path("/products/**")
                        .and()
                        .method("GET")
                        .uri(serviceUri))
                .route(r -> r.path("/products")
                        .and()
                        .method("GET")
                        .uri(serviceUri))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productsRoutes(ProductDetailsHandlers productDetailsHandlers) {
        return RouterFunctions
                .route(POST("/products"), productDetailsHandlers::postNewProduct)
                .andRoute(GET("/products/{productId}"), productDetailsHandlers::getProductDetails)
                .andRoute(PUT("/products/{productId}"), productDetailsHandlers::updateProduct)
                .andRoute(GET("/products"), productDetailsHandlers::getProducts);
    }
}
