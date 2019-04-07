package demo.sa.apigateway.proxies;

import demo.sa.customer.model.dto.Customer;
import demo.sa.order.model.dto.AddressDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class CustomerProxyService {

    private final WebClient webClient;
    private final String serviceUrl;

    public CustomerProxyService(@Qualifier("customerWebClient") WebClient webClient,
                                @Value("${customer.services_url:localhost:8081}") String serviceUrl) {
        this.webClient = webClient;
        this.serviceUrl = serviceUrl;
    }

    public Mono<Customer> findCustomerById(String customerId) {
        Mono<ClientResponse> response = webClient.get()
                .uri(serviceUrl + "customer")
                .attribute("customerId", customerId)
                .exchange();
        return response.flatMap(resp -> {
            switch (resp.statusCode()) {
                case OK:
                    return resp.bodyToMono(Customer.class);
                case NOT_FOUND:
                    return Mono.empty();
                default:
                    return Mono.error(new RuntimeException("Unknown" + resp.statusCode()));
            }
        });
    }

    public Mono<Customer> addAddresses(List<AddressDto> addresses) {
        return null;
    }
}
