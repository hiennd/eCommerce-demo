package demo.sa.apigateway;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.sa.order.model.dto.OrderDto;
import demo.sa.product.model.dto.Product;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.UUID;


public class ProductDetailsIT extends ApiGatewayApplicationIntegrationTests {

    private WebTestClient webClient;

    private String baseUri;

    @LocalServerPort
    private int port = 0;



    @Before
    public void setUp()  {
        baseUri = "http://localhost:" + port;
        this.webClient = WebTestClient.bindToServer()
                .responseTimeout(Duration.ofSeconds(10)).baseUrl(baseUri).build();
    }

    @Test
    public void products_push() throws IOException, URISyntaxException {
        URI uri = getClass().getClassLoader()
                .getResource("products.post.json").toURI();

        Product product = new ObjectMapper()
                .readValue(uri.toURL(),
                        Product.class);
        product.setProductId(UUID.randomUUID().toString());

        webClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(product))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void products_get() {
        webClient.get().uri("/products").exchange().expectStatus().isOk();
    }
    
}
