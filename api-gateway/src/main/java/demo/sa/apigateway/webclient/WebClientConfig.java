package demo.sa.apigateway.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${orders.author_header_value}")
    private String authorHeaderValue;

    @Bean
    public WebClient orderWebClient() {
        return WebClient.builder()
                .defaultHeader("Authorization", authorHeaderValue)
                .build();
    }

    @Bean
    public WebClient productWebClient() {
        return WebClient.builder()
                .build();
    }

    @Bean
    public WebClient priceWebClient() {
        return WebClient.builder()
                .build();
    }

    @Bean
    public WebClient customerWebClient() {
        return WebClient.builder()
                .build();
    }
}
