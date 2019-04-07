package demo.sa.apigateway.orders;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class OrderRouter {

    @Value("${orders.service_url:localhost:8084}")
    private String orderServiceUri;


    @Bean
    public RouteLocator orderProxyRouting(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/orders/**")
                        .and()
                        .method("POST")
                        .uri(orderServiceUri))
                .route(r -> r.path("/orders/**")
                        .and()
                        .method("PUT")
                        .uri(orderServiceUri))
                .route(r -> r.path("/orders")
                        .and()
                        .method("GET")
                        .uri(orderServiceUri))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderRoutes(OrderHandlers orderHandlers) {
        return RouterFunctions.route(GET("/orders/{orderId}"), orderHandlers::getOrderDetails)
                .andRoute(POST("/orders"), orderHandlers::postNewOrder)
                .andRoute(POST("/orders"), orderHandlers::updateAnOrder)
                .andRoute(GET("/orders"), orderHandlers::getOrders);
    }
}
