package demo.sa.apigateway.proxies;

import demo.sa.apigateway.exception.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PriceProxyService {

    private final WebClient webClient;
    private final String serviceUrl;

    public PriceProxyService(@Qualifier("priceWebClient") WebClient webClient,
                             @Value("${price.services_url:localhost:8083}") String serviceUrl) {
        this.webClient = webClient;
        this.serviceUrl = serviceUrl;
    }

    public Mono<Float> getPrice(String productId, String promotionId) {
        Mono<ClientResponse> response = webClient.get()
                .uri(serviceUrl + "prices")
                .attribute("productId", productId)
                .attribute("promotionId", promotionId)
                .exchange();
        return response.flatMap(resp -> {
            switch (resp.statusCode()) {
                case OK:
                    return resp.bodyToMono(Float.class);
                case NOT_FOUND:
                    return Mono.error(new OrderNotFoundException());
                default:
                    return Mono.error(new PriceNotFoundException("Unknown" + resp.statusCode()));
            }
        });
    }
}
