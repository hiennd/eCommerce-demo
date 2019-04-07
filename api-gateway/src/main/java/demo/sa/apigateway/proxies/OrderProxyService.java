package demo.sa.apigateway.proxies;

import demo.sa.apigateway.exception.*;
import demo.sa.order.model.dto.OrderDto;
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
public class OrderProxyService {

    private WebClient client;
    private String orderServiceUri;

    @Autowired
    public OrderProxyService(@Qualifier("orderWebClient") WebClient client,
                             @Value("${orders.services_url:localhost:8084}") String orderServiceUri) {
        this.client = client;
        this.orderServiceUri = orderServiceUri;
    }

    public Mono<OrderDto> findOrderById(String orderId) {
        Mono<ClientResponse> response = client
                .get()
                .uri(orderServiceUri + "/orders/{orderId}", orderId)
                .exchange();
        return response.flatMap(resp -> {
            switch (resp.statusCode()) {
                case OK:
                    return resp.bodyToMono(OrderDto.class);
                case NOT_FOUND:
                    return Mono.error(new OrderNotFoundException());
                default:
                    return Mono.error(new RuntimeException("Unknown" + resp.statusCode()));
            }
        });
    }

    public Mono<OrderDto> postNewOrder(OrderDto od) {
        Mono<OrderDto> orderDtoMono = client
                .post()
                .uri(orderServiceUri + "/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(od))
                .retrieve()
                .bodyToMono(OrderDto.class);
        return orderDtoMono.flatMap(Mono::just);
    }

    public Flux<OrderDto> getOrders() {
        return client.get()
                .uri(orderServiceUri + "/orders")
                .retrieve()
                .bodyToFlux(OrderDto.class);
    }

    public Mono<OrderDto> updateOrder(OrderDto orderDto) {
        Mono<OrderDto> orderDtoMono = client
                .put()
                .uri(orderServiceUri + "/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(orderDto))
                .retrieve()
                .bodyToMono(OrderDto.class);
        return orderDtoMono.flatMap(Mono::just);
    }
}
