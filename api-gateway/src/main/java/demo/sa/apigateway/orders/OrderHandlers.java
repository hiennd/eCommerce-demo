package demo.sa.apigateway.orders;

import demo.sa.apigateway.exception.*;
import demo.sa.apigateway.proxies.OrderProxyService;
import demo.sa.apigateway.proxies.PriceProxyService;
import demo.sa.order.model.dto.OrderDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class OrderHandlers {

    private final OrderProxyService orderService;

    private final PriceProxyService priceService;

    @Autowired
    public OrderHandlers(OrderProxyService orderService, PriceProxyService priceService) {
        this.orderService = orderService;
        this.priceService = priceService;
    }

    public Mono<ServerResponse> getOrderDetails(ServerRequest serverRequest) {

        String orderId = serverRequest.pathVariable("orderId");

        Mono<OrderDto> orderInfo = orderService.findOrderById(orderId);

        return orderInfo.flatMap(od -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(od)))
                .onErrorResume(OrderNotFoundException.class, e -> ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> postNewOrder(ServerRequest serverRequest) {
        return saveOrder(serverRequest, orderService::postNewOrder);
    }

    public Mono<ServerResponse> updateAnOrder(ServerRequest serverRequest) {
        return saveOrder(serverRequest, orderService::updateOrder);
    }

    @NotNull
    private Mono<ServerResponse> saveOrder(ServerRequest serverRequest,
                                           Function<OrderDto, Mono<? extends OrderDto>> monoOrderFunction) {
        Mono<OrderDto> orderRequestDto = serverRequest.bodyToMono(OrderDto.class);
        Mono<OrderDto> priceAddedOrderDto = orderRequestDto
                .flatMap(
                        od -> Flux.fromIterable(od.getProducts())
                                .flatMap(productDto ->
                                        priceService.getPrice(productDto.getProductId(), productDto.getPromotionId())
                                                .flatMap(price -> {
                                                    Float newPrice = productDto.getPrice() + price;
                                                    productDto.setPrice(newPrice);
                                                    return Mono.just(productDto);
                                                })
                                                .onErrorReturn(productDto)
                                )
                                .collectList()
                                .flatMap(productDtos -> Mono.just(od)))
                .onErrorResume(PriceNotFoundException.class, e -> orderRequestDto);

        return priceAddedOrderDto
                .flatMap(monoOrderFunction)
                .flatMap(od -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(od)))
                .onErrorResume(HttpStatusCodeException.class, e -> ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> getOrders(ServerRequest serverRequest) {
        Flux<OrderDto> orders = orderService.getOrders();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(orders, OrderDto.class));
    }
}
